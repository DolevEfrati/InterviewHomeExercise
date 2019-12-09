package modules;

import java.sql.Timestamp;

public class PageView {
    private String visitorId;
    private String siteUrl;
    private String pageViewUrl;
    private Timestamp timestamp;

    public PageView(final String visitorId,
                    final String siteUrl,
                    final String pageViewUrl,
                    final Timestamp timestamp) {
        this.visitorId = visitorId;
        this.siteUrl = siteUrl;
        this.pageViewUrl = pageViewUrl;
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public String getVisitorId() {
        return this.visitorId;
    }

    public String getSiteUrl() {
        return this.siteUrl;
    }
}
