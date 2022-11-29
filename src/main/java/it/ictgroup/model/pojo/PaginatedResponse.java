package it.ictgroup.model.pojo;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

@SuppressWarnings("unused")
public class PaginatedResponse<T> {

    // FROM ASSET RS!

    final long total;
    String scrollId;

    final List<T> list;

    public PaginatedResponse() {
        this(0, Collections.emptyList());
    }

    public PaginatedResponse(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PaginatedResponse(long total, String scrollId, List<T> list) {
        this(total, list);
        if (list != null && !list.isEmpty()) {
            this.scrollId = scrollId;
        }
    }

    public long getTotal() {
        return total;
    }

    public List<T> getList() {
        return list;
    }

    public String getScrollId() {
        return scrollId;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PaginatedResponse.class.getSimpleName() + "[", "]")
                .add("total=" + total)
                .add("scrollId=" + scrollId)
                .add("list=" + list)
                .toString();
    }
}
