package com.sandi.web.common.persistence.entity;

import com.sandi.web.common.id.IdGeneratorFactory;
import com.sandi.web.common.persistence.SQLHelper;
import com.sandi.web.common.persistence.annotation.Column;
import com.sandi.web.common.utils.DateUtils;
import com.sandi.web.utils.common.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by dizl on 2015/5/11.
 */
public abstract class BaseEntity implements Serializable {
    @Column(isColumn = false)
    @JsonIgnore
    protected static final Long serialVersionUID = -1941046831377985500L;
    @Column(isColumn = false)
    @JsonIgnore
    private Page page;
    @JsonIgnore
    @Column(isColumn = false)
    private Rank[] ranks;

    /**
     * 获取主键
     */
    public Long newId() throws Exception {
        String tableName = SQLHelper.getSimpleTableName(this.getClass());
        return IdGeneratorFactory.newId(tableName);
    }

    /**
     * 获取string类型的主键
     */
    public String newStringId() throws Exception {
        String tableName = SQLHelper.getTableName(this.getClass());
        return IdGeneratorFactory.newStringId(tableName);
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Rank[] getRanks() {
        return ranks;
    }

    public void setRanks(Rank[] ranks) {
        this.ranks = ranks;
    }

    public void setRank(Rank rank) {
        Rank[] ranks = new Rank[1];
        ranks[0] = rank;
        this.setRanks(ranks);
    }

    public void set(String key, Object value) throws Exception {
        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (("set" + StringUtils.toCapitalizeCamelCase(key)).equals(methods[i].getName())) {
                switch (methods[i].getParameterTypes()[0].getName()) {
                    case "java.lang.String": {
                        methods[i].invoke(this, StringUtils.toString(value));
                        break;
                    }
                    case "java.lang.Long": {
                        methods[i].invoke(this, StringUtils.toLong(value));
                        break;
                    }
                    case "java.lang.Integer": {
                        methods[i].invoke(this, StringUtils.toInteger(value));
                        break;
                    }
                    case "class java.util.Date": {
                        methods[i].invoke(this, DateUtils.parseDate(value));
                        break;
                    }
                }
            }
        }
    }

    public Object get(String key) throws Exception {
        if (key.indexOf("_") > 0) {
            key = StringUtils.toCapitalizeCamelCase(key);
        } else {
            key = key.substring(0, 1).toUpperCase() + key.substring(1);
        }

        Method[] methods = this.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (("get" + key).equals(methods[i].getName())) {
                Object result = methods[i].invoke(this);
                return result;
            }
        }
        return null;
    }
}