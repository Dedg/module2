import java.util.*;
import java.text.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
* Containing items and calculating price.
*/
public class ShoppingCart {
    public static enum ItemType { NEW, REGULAR, SECOND_FREE, SALE };
    /**
     * Adds new item.
     *
     * @param title item title 1 to 32 symbols
     * @param price item ptice in USD, > 0
     * @param quantity item quantity, from 1
     * @param type item type
     *
     * @throws IllegalArgumentException if some value is wrong
     */
    public void addItem(String title, double price, int quantity, ItemType type){
        if (title == null || title.length() == 0 || title.length() > 32)
            throw new IllegalArgumentException("Illegal title");
        if (price < 0.01)
            throw new IllegalArgumentException("Illegal price");
        if (quantity <= 0)
            throw new IllegalArgumentException("Illegal quantity");
        items.add(new Item(title, price, quantity, type));
    }

    /**
     * Formats shopping price.
     *
     * @return string as lines, separated with \n,
     * first line: # Item Price Quan. Discount Total
     * second line: ---------------------------------------------------------
     * next lines: NN Title $PP.PP Q DD% $TT.TT
     * 1 Some title $.30 2 - $.60
     * 2 Some very long $100.00 1 50% $50.00
     * ...
     * 31 Item 42 $999.00 1000 - $999000.00
     * end line: ---------------------------------------------------------
     * last line: 31 $999050.60
     *
     * if no items in cart returns "No items." string.
     */
    public String formatTicket(){
        if (items.size() == 0)
            return "No items.";

        double total = items.stream().mapToDouble(Item::getTotal).sum();
        List<String[]> lines = convertItemsToTableLines();

        return formatTicketTable(total, lines);
    }

    // --- private section -----------------------------------------------------
    private static String formatTicketTable(double total, List<String[]> lines) {
        String[] header = {"#", "Item", "Price", "Quan.", "Discount", "Total"};
        String[] footer = {String.valueOf(lines.size()), "", "", "", "", MONEY.format(total)};
        int[] align = new int[]{1, -1, 1, 1, 1, 1};
        int[] width = new int[]{0, 0, 0, 0, 0, 0};
        for (String[] line : lines)
            adjustColumnWidth(width, line);
        adjustColumnWidth(width, header);
        adjustColumnWidth(width, footer);
        int lineLength = IntStream.of(width).sum() + width.length - 1;
        StringBuilder sb = new StringBuilder();
        appendFormattedLine(header, align, width, sb, true);
        appendSeparator(lineLength, sb);
        for (String[] line : lines) {
            appendFormattedLine(line, align, width, sb, true);
        }
        appendSeparator(lineLength, sb);
        appendFormattedLine(footer, align, width, sb, false);
        return sb.toString();
    }

    /**
     * Appends separator to sb
     * @param lineLength specifies length of separator line
     * @param sb StringBuilder to append value to
     */
    private static void appendSeparator(int lineLength, StringBuilder sb) {
        sb.append("-".repeat(lineLength));
        sb.append("\n");
    }

    /**
     * Appends to sb formatted value of all lines
     * @param lines string array of lines
     * @param align -1 for align left, 0 for center and +1 for align right.
     * @param width of line
     * @param sb StringBuilder to append value to
     * @param newLine specifies whether new line should be appended
     */
    private static void appendFormattedLine(String[] lines, int[] align, int[] width, StringBuilder sb, Boolean newLine) {
        for (int i = 0; i < lines.length; i++)
            appendFormatted(sb, lines[i], align[i], width[i]);
        if (newLine) sb.append("\n");
    }

    /**
     * Adgust column width
     * @param width expected width of the column
     * @param columns list
     */
    private static void adjustColumnWidth(int[] width, String[] columns) {
        for (int i = 0; i < columns.length; i++)
            width[i] = (int) Math.max(width[i], columns[i].length());
    }

    private List<String[]> convertItemsToTableLines() {
        AtomicInteger index = new AtomicInteger();
        return items
                .stream()
                .map(item -> new String[]{
                        String.valueOf(index.incrementAndGet()),
                        item.getTitle(),
                        MONEY.format(item.getPrice()),
                        String.valueOf(item.getQuantity()),
                        (item.getDiscount() == 0) ? "-" : (item.getDiscount() + "%"),
                        MONEY.format(item.getTotal())
                }).collect(Collectors.toList());
    }

    private static final NumberFormat MONEY;
        static {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            MONEY = new DecimalFormat("$#.00", symbols);
        }
    /**
     * Appends to sb formatted value.
     * Trims string if its length > width.
     * @param align -1 for align left, 0 for center and +1 for align right.
     */
    public static void appendFormatted(StringBuilder sb, String value, int align, int width){
        if (value.length() > width)
            value = value.substring(0,width);
        int before = (align == 0)
            ? (width - value.length()) / 2
            : (align == -1) ? 0 : width - value.length();
        int after = width - value.length() - before;
        while (before-- > 0)
            sb.append(" ");
        sb.append(value);
        while (after-- > 0)
            sb.append(" ");
        sb.append(" ");
    }

    /** Container for added items */
    private List<Item> items = new ArrayList<Item>();
}
