package Utils;

import modules.*;
import java.util.*;

public class Manager {
    public static Map<String, Map<String, List<PageView>>> visitorsPerSite = new HashMap<>();
    public static Map<String, List<Session>> sessionListPerSiteUrl = new HashMap<>();

    public static void addNewPageView(PageView pageView) {
        String siteUrl = pageView.getSiteUrl();
        String visitorId = pageView.getVisitorId();

        if (!visitorsPerSite.containsKey(siteUrl)) {
            visitorsPerSite.put(siteUrl, new HashMap<>());
        }
        if (!visitorsPerSite.get(siteUrl).containsKey(visitorId)) {
            visitorsPerSite.get(siteUrl).put(visitorId, new ArrayList<>());
        }
        visitorsPerSite.get(siteUrl).get(visitorId).add(pageView);
    }

    public static Integer getNumSitesByVisitorId(String visitorId) {
        int count = 0;
        for (Map<String, List<PageView>> mapToSite : visitorsPerSite.values())
            if (mapToSite.containsKey(visitorId))
                count++;
        return count != 0 ? count : null;
    }

    public static Integer getSessionsNumberBySiteUrl(String siteUrl) {
        return sessionListPerSiteUrl.containsKey(siteUrl)? sessionListPerSiteUrl.get(siteUrl).size() : null;
    }

    public static void sortPagesViewsToSessions() {
        final int MAX_TIMESTAMP_BETWEEN_VIEWS = 1800;
        for (String siteUrl: visitorsPerSite.keySet()) {
            final Map<String, List<PageView>> pagesViewsPerVisitorMap = visitorsPerSite.get(siteUrl);
            List<Session> sessionsList = new ArrayList<>();
            for (List<PageView> pagesViewsList: pagesViewsPerVisitorMap.values()) {
                if (pagesViewsList.size() == 1) {
                    sessionsList.add(new Session(pagesViewsList.get(0)));
                } else {
                    pagesViewsList.sort(Comparator.comparing(PageView::getTimestamp));
                    Session currSession = new Session(pagesViewsList.get(0));

                    for (PageView pageView: pagesViewsList) {
                        if ((pageView.getTimestamp().getTime() - currSession.getEnd().getTime())
                                <= MAX_TIMESTAMP_BETWEEN_VIEWS) {
                            currSession.addPageView(pageView);
                        } else {
                            sessionsList.add(currSession);
                            currSession = new Session(pageView);
                        }
                    }
                    sessionsList.add(currSession);
                }
            }
            sessionListPerSiteUrl.put(siteUrl, sessionsList);
        }
    }

    public static Double getMedianSessionsBySiteUrl(String siteUrl) {
        if (!visitorsPerSite.containsKey(siteUrl)) {
            return null;
        }

        List<Session> sessionsLengthList = sessionListPerSiteUrl.get(siteUrl);
        final int listSize = sessionsLengthList.size();
        return listSize % 2 != 0 ? sessionsLengthList.get(listSize / 2).getLength() :
                (sessionsLengthList.get(listSize / 2).getLength() +
                        sessionsLengthList.get((listSize / 2) - 1).getLength()) / 2.0;
    }

    public static void sortSessionsByLengths() {
        sessionListPerSiteUrl.values()
                .forEach(sessionList -> sessionList.sort(Comparator.comparing(Session::getLength)));
    }
}
