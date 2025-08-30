package com.nicenpc.application.query;

/**
 * 根據電子郵件網域查詢使用者
 */
public class FindByEmailDomainQuery {

    private final String domain;

    public FindByEmailDomainQuery(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
