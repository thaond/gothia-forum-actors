package se.gothiaforum.util.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class PageIterator.
 * 
 * @author Simon Göransson
 * @company Monator Technologies AB
 * 
 */
public class PageIterator {

    /** The total hits. */
    private int totalHits;

    /** The current page. */
    private int currentPage;

    /** The page size. */
    private int pageSize;

    /** The max pages. */
    private int maxPages;

    /** The show first. */
    private boolean showFirst;

    /** The show previous. */
    private boolean showPrevious;

    /** The show next. */
    private boolean showNext;

    /** The show last. */
    private boolean showLast;

    /** Show pageinator. */
    private boolean showPaginator;

    /** The pages. */
    private List<PageIteratorPage> pages;

    /**
     * Instantiates a new page iterator.
     * 
     * @param totalHits
     *            the total hits
     * @param currentPage
     *            the current page
     * @param pageSize
     *            the page size
     * @param maxPages
     *            the max pages
     */
    public PageIterator(int totalHits, int currentPage, int pageSize, int maxPages) {
        this.totalHits = totalHits;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.maxPages = maxPages;

        showPaginator = true;

        pages = new ArrayList<PageIteratorPage>();

        int numberOfPages = totalHits / pageSize;

        if (numberOfPages > maxPages) {
            numberOfPages = maxPages;
        }

        for (int i = 1; i <= numberOfPages + 1; i++) {

            PageIteratorPage page = new PageIteratorPage();
            page.setPagenumber(i);

            if (i == currentPage) {
                page.setSelected(true);
            }
            if (i % 2 == 0) {
                page.setOdd(false);
            } else {
                page.setOdd(true);
            }

            pages.add(page);
        }

        if (currentPage == 1) {
            this.setShowFirst(false);
            this.setShowPrevious(false);
        } else {
            this.setShowFirst(true);
            this.setShowPrevious(true);
        }

        if (currentPage == pages.size()) {
            this.setShowNext(false);
            this.setShowLast(false);
        } else {
            this.setShowNext(true);
            this.setShowLast(true);
        }

        if (pages.size() <= 1) {
            showPaginator = false;
        }

    }

    /**
     * Gets the total hits.
     * 
     * @return the total hits
     */
    public int getTotalHits() {
        return totalHits;
    }

    /**
     * Sets the total hits.
     * 
     * @param totalHits
     *            the new total hits
     */
    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    /**
     * Gets the current page.
     * 
     * @return the current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Sets the current page.
     * 
     * @param currentPage
     *            the new current page
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Gets the page size.
     * 
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the page size.
     * 
     * @param pageSize
     *            the new page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets the max pages.
     * 
     * @return the max pages
     */
    public int getMaxPages() {
        return maxPages;
    }

    /**
     * Sets the max pages.
     * 
     * @param maxPages
     *            the new max pages
     */
    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    /**
     * Gets the show first.
     * 
     * @return the show first
     */
    public boolean getShowFirst() {
        return showFirst;
    }

    /**
     * Sets the show first.
     * 
     * @param showFirst
     *            the new show first
     */
    public void setShowFirst(boolean showFirst) {
        this.showFirst = showFirst;
    }

    /**
     * Gets the show previous.
     * 
     * @return the show previous
     */
    public boolean getShowPrevious() {
        return showPrevious;
    }

    /**
     * Sets the show previous.
     * 
     * @param showPrevious
     *            the new show previous
     */
    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    /**
     * Gets the show next.
     * 
     * @return the show next
     */
    public boolean getShowNext() {
        return showNext;
    }

    /**
     * Sets the show next.
     * 
     * @param showNext
     *            the new show next
     */
    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    /**
     * Gets the show last.
     * 
     * @return the show last
     */
    public boolean getShowLast() {
        return showLast;
    }

    /**
     * Sets the show last.
     * 
     * @param showLast
     *            the new show last
     */
    public void setShowLast(boolean showLast) {
        this.showLast = showLast;
    }

    /**
     * Gets the pages.
     * 
     * @return the pages
     */
    public List<PageIteratorPage> getPages() {
        return pages;
    }

    /**
     * Sets the pages.
     * 
     * @param pages
     *            the new pages
     */
    public void setPages(List<PageIteratorPage> pages) {
        this.pages = pages;
    }

    /**
     * Gets the previous.
     * 
     * @return the previous
     */
    public int getPrevious() {
        return currentPage - 1;
    }

    /**
     * Gets the next.
     * 
     * @return the next
     */
    public int getNext() {
        return currentPage + 1;
    }

    /**
     * Gets the last.
     * 
     * @return the last
     */
    public int getLast() {
        return pages.size();
    }

    /**
     * @return the showPageInator
     */
    public boolean getShowPaginator() {
        return showPaginator;
    }

    /**
     * @param showPageInator
     *            the showPageInator to set
     */
    public void setShowPaginator(boolean showPaginator) {
        this.showPaginator = showPaginator;
    }

}
