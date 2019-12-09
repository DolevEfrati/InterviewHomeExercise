package modules;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Session implements Comparable<Session> {
    private List<PageView> pagesViewsList;
    private Timestamp start;
    private Timestamp end;

    public Session(@NotNull PageView firstPageView) {
        this.pagesViewsList = new ArrayList<>();
        this.pagesViewsList.add(firstPageView);
        this.start = firstPageView.getTimestamp();
        this.end = this.start;
    }

    public Long getLength() {
        return end.getTime() - start.getTime();
    }

    public void addPageView(PageView pageView) {
        if (this.pagesViewsList.isEmpty()) {
            this.start = pageView.getTimestamp();
            this.end = this.start;
        } else if (pageView.getTimestamp().after(this.end)) {
            this.end = pageView.getTimestamp();
        } else if (pageView.getTimestamp().before(this.start)) {
            this.start = pageView.getTimestamp();
        }
        this.pagesViewsList.add(pageView);
    }

    @Override
    public int compareTo(Session session) {
        return this.getStart().compareTo(session.getEnd());
    }

    public Timestamp getStart() {
        return this.start;
    }

    public Timestamp getEnd() {
        return this.end;
    }
}
