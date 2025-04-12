package business;

import java.util.List;

/**
 * Lớp hỗ trợ phân trang
 */
public class Pagination<T> {
    private List<T> items;
    private int currentPage;
    private int itemsPerPage;
    private int totalItems;

    public Pagination(List<T> items, int currentPage, int itemsPerPage, int totalItems) {
        this.items = items;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalItems = totalItems;
    }

    public List<T> getItems() { return items; }
    public int getCurrentPage() { return currentPage; }
    public int getTotalPages() { return (int) Math.ceil((double) totalItems / itemsPerPage); }
    public int getTotalItems() { return totalItems; }
    public boolean hasPrevious() { return currentPage > 1; }
    public boolean hasNext() { return currentPage < getTotalPages(); }
}