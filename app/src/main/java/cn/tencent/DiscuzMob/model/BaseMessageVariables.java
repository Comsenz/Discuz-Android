package cn.tencent.DiscuzMob.model;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by kurt on 15-6-12.
 */
public class BaseMessageVariables<D> extends BaseVariables {

    private List<D> list;
    private String count;
    private int perpage;
    private int page;
    private String pmid;

    public BaseMessageVariables() {
    }

    public boolean isFinishAll() {
        return page * perpage >= count();
    }

    public int count() {
        return TextUtils.isEmpty(count) ? 0 : Integer.parseInt(count);
    }

    public List<D> getList() {
        return list;
    }

    public void setList(List<D> list) {
        this.list = list;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getPmid() {
        return pmid;
    }

    public void setPmid(String pmid) {
        this.pmid = pmid;
    }

}
