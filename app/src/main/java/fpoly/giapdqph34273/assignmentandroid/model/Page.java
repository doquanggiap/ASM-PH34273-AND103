package fpoly.giapdqph34273.assignmentandroid.model;

public class Page<T> {
    private T data;
    private int currentPage, totalPage;

    public Page(T data, int currentPage, int totalPage) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }

    public Page() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}