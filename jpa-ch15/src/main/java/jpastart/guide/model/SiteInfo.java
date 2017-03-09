package jpastart.guide.model;

import javax.persistence.Embeddable;

@Embeddable
public class SiteInfo {
    private String site;
    private int time;

    public SiteInfo() {
    }

    public SiteInfo(String site, int time) {
        this.site = site;
        this.time = time;
    }

    public String getSite() {
        return site;
    }

    public int getTime() {
        return time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SiteInfo siteInfo = (SiteInfo) o;

        if (time != siteInfo.time) return false;
        return site != null ? site.equals(siteInfo.site) : siteInfo.site == null;

    }

    @Override
    public int hashCode() {
        int result = site != null ? site.hashCode() : 0;
        result = 31 * result + time;
        return result;
    }
}
