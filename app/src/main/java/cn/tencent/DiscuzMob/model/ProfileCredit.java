package cn.tencent.DiscuzMob.model;

/**
 * Created by kurt on 15-6-16.
 */
public class ProfileCredit {
    private String title;
    private String ratio;
    private String img;
    private String unit;

    public ProfileCredit() {
    }

    public ProfileCredit(String title, String ratio) {
        this.title = title;
        this.ratio = ratio;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
