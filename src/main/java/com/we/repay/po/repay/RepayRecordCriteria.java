package com.we.repay.po.repay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepayRecordCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public RepayRecordCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andWrIdIsNull() {
            addCriterion("wr_id is null");
            return (Criteria) this;
        }

        public Criteria andWrIdIsNotNull() {
            addCriterion("wr_id is not null");
            return (Criteria) this;
        }

        public Criteria andWrIdEqualTo(Long value) {
            addCriterion("wr_id =", value, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdNotEqualTo(Long value) {
            addCriterion("wr_id <>", value, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdGreaterThan(Long value) {
            addCriterion("wr_id >", value, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdGreaterThanOrEqualTo(Long value) {
            addCriterion("wr_id >=", value, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdLessThan(Long value) {
            addCriterion("wr_id <", value, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdLessThanOrEqualTo(Long value) {
            addCriterion("wr_id <=", value, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdIn(List<Long> values) {
            addCriterion("wr_id in", values, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdNotIn(List<Long> values) {
            addCriterion("wr_id not in", values, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdBetween(Long value1, Long value2) {
            addCriterion("wr_id between", value1, value2, "wrId");
            return (Criteria) this;
        }

        public Criteria andWrIdNotBetween(Long value1, Long value2) {
            addCriterion("wr_id not between", value1, value2, "wrId");
            return (Criteria) this;
        }

        public Criteria andWaIdIsNull() {
            addCriterion("wa_id is null");
            return (Criteria) this;
        }

        public Criteria andWaIdIsNotNull() {
            addCriterion("wa_id is not null");
            return (Criteria) this;
        }

        public Criteria andWaIdEqualTo(Long value) {
            addCriterion("wa_id =", value, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdNotEqualTo(Long value) {
            addCriterion("wa_id <>", value, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdGreaterThan(Long value) {
            addCriterion("wa_id >", value, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdGreaterThanOrEqualTo(Long value) {
            addCriterion("wa_id >=", value, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdLessThan(Long value) {
            addCriterion("wa_id <", value, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdLessThanOrEqualTo(Long value) {
            addCriterion("wa_id <=", value, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdIn(List<Long> values) {
            addCriterion("wa_id in", values, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdNotIn(List<Long> values) {
            addCriterion("wa_id not in", values, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdBetween(Long value1, Long value2) {
            addCriterion("wa_id between", value1, value2, "waId");
            return (Criteria) this;
        }

        public Criteria andWaIdNotBetween(Long value1, Long value2) {
            addCriterion("wa_id not between", value1, value2, "waId");
            return (Criteria) this;
        }

        public Criteria andOrdIdIsNull() {
            addCriterion("ord_id is null");
            return (Criteria) this;
        }

        public Criteria andOrdIdIsNotNull() {
            addCriterion("ord_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrdIdEqualTo(String value) {
            addCriterion("ord_id =", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdNotEqualTo(String value) {
            addCriterion("ord_id <>", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdGreaterThan(String value) {
            addCriterion("ord_id >", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdGreaterThanOrEqualTo(String value) {
            addCriterion("ord_id >=", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdLessThan(String value) {
            addCriterion("ord_id <", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdLessThanOrEqualTo(String value) {
            addCriterion("ord_id <=", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdLike(String value) {
            addCriterion("ord_id like", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdNotLike(String value) {
            addCriterion("ord_id not like", value, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdIn(List<String> values) {
            addCriterion("ord_id in", values, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdNotIn(List<String> values) {
            addCriterion("ord_id not in", values, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdBetween(String value1, String value2) {
            addCriterion("ord_id between", value1, value2, "ordId");
            return (Criteria) this;
        }

        public Criteria andOrdIdNotBetween(String value1, String value2) {
            addCriterion("ord_id not between", value1, value2, "ordId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdIsNull() {
            addCriterion("wx_open_id is null");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdIsNotNull() {
            addCriterion("wx_open_id is not null");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdEqualTo(String value) {
            addCriterion("wx_open_id =", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdNotEqualTo(String value) {
            addCriterion("wx_open_id <>", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdGreaterThan(String value) {
            addCriterion("wx_open_id >", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdGreaterThanOrEqualTo(String value) {
            addCriterion("wx_open_id >=", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdLessThan(String value) {
            addCriterion("wx_open_id <", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdLessThanOrEqualTo(String value) {
            addCriterion("wx_open_id <=", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdLike(String value) {
            addCriterion("wx_open_id like", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdNotLike(String value) {
            addCriterion("wx_open_id not like", value, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdIn(List<String> values) {
            addCriterion("wx_open_id in", values, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdNotIn(List<String> values) {
            addCriterion("wx_open_id not in", values, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdBetween(String value1, String value2) {
            addCriterion("wx_open_id between", value1, value2, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andWxOpenIdNotBetween(String value1, String value2) {
            addCriterion("wx_open_id not between", value1, value2, "wxOpenId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Short value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Short value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Short value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Short value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Short value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Short value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Short> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Short> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Short value1, Short value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Short value1, Short value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Float value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Float value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Float value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Float value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Float value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Float value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Float> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Float> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Float value1, Float value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Float value1, Float value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andCreateDtmIsNull() {
            addCriterion("create_dtm is null");
            return (Criteria) this;
        }

        public Criteria andCreateDtmIsNotNull() {
            addCriterion("create_dtm is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDtmEqualTo(Date value) {
            addCriterion("create_dtm =", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmNotEqualTo(Date value) {
            addCriterion("create_dtm <>", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmGreaterThan(Date value) {
            addCriterion("create_dtm >", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("create_dtm >=", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmLessThan(Date value) {
            addCriterion("create_dtm <", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmLessThanOrEqualTo(Date value) {
            addCriterion("create_dtm <=", value, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmIn(List<Date> values) {
            addCriterion("create_dtm in", values, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmNotIn(List<Date> values) {
            addCriterion("create_dtm not in", values, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmBetween(Date value1, Date value2) {
            addCriterion("create_dtm between", value1, value2, "createDtm");
            return (Criteria) this;
        }

        public Criteria andCreateDtmNotBetween(Date value1, Date value2) {
            addCriterion("create_dtm not between", value1, value2, "createDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmIsNull() {
            addCriterion("update_dtm is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmIsNotNull() {
            addCriterion("update_dtm is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmEqualTo(Date value) {
            addCriterion("update_dtm =", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmNotEqualTo(Date value) {
            addCriterion("update_dtm <>", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmGreaterThan(Date value) {
            addCriterion("update_dtm >", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmGreaterThanOrEqualTo(Date value) {
            addCriterion("update_dtm >=", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmLessThan(Date value) {
            addCriterion("update_dtm <", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmLessThanOrEqualTo(Date value) {
            addCriterion("update_dtm <=", value, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmIn(List<Date> values) {
            addCriterion("update_dtm in", values, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmNotIn(List<Date> values) {
            addCriterion("update_dtm not in", values, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmBetween(Date value1, Date value2) {
            addCriterion("update_dtm between", value1, value2, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andUpdateDtmNotBetween(Date value1, Date value2) {
            addCriterion("update_dtm not between", value1, value2, "updateDtm");
            return (Criteria) this;
        }

        public Criteria andPayManIsNull() {
            addCriterion("pay_man is null");
            return (Criteria) this;
        }

        public Criteria andPayManIsNotNull() {
            addCriterion("pay_man is not null");
            return (Criteria) this;
        }

        public Criteria andPayManEqualTo(String value) {
            addCriterion("pay_man =", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManNotEqualTo(String value) {
            addCriterion("pay_man <>", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManGreaterThan(String value) {
            addCriterion("pay_man >", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManGreaterThanOrEqualTo(String value) {
            addCriterion("pay_man >=", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManLessThan(String value) {
            addCriterion("pay_man <", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManLessThanOrEqualTo(String value) {
            addCriterion("pay_man <=", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManLike(String value) {
            addCriterion("pay_man like", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManNotLike(String value) {
            addCriterion("pay_man not like", value, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManIn(List<String> values) {
            addCriterion("pay_man in", values, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManNotIn(List<String> values) {
            addCriterion("pay_man not in", values, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManBetween(String value1, String value2) {
            addCriterion("pay_man between", value1, value2, "payMan");
            return (Criteria) this;
        }

        public Criteria andPayManNotBetween(String value1, String value2) {
            addCriterion("pay_man not between", value1, value2, "payMan");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNull() {
            addCriterion("attribute1 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNotNull() {
            addCriterion("attribute1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute1EqualTo(String value) {
            addCriterion("attribute1 =", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotEqualTo(String value) {
            addCriterion("attribute1 <>", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThan(String value) {
            addCriterion("attribute1 >", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThanOrEqualTo(String value) {
            addCriterion("attribute1 >=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThan(String value) {
            addCriterion("attribute1 <", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThanOrEqualTo(String value) {
            addCriterion("attribute1 <=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Like(String value) {
            addCriterion("attribute1 like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotLike(String value) {
            addCriterion("attribute1 not like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1In(List<String> values) {
            addCriterion("attribute1 in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotIn(List<String> values) {
            addCriterion("attribute1 not in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Between(String value1, String value2) {
            addCriterion("attribute1 between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotBetween(String value1, String value2) {
            addCriterion("attribute1 not between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNull() {
            addCriterion("attribute2 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNotNull() {
            addCriterion("attribute2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute2EqualTo(String value) {
            addCriterion("attribute2 =", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotEqualTo(String value) {
            addCriterion("attribute2 <>", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThan(String value) {
            addCriterion("attribute2 >", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThanOrEqualTo(String value) {
            addCriterion("attribute2 >=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThan(String value) {
            addCriterion("attribute2 <", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThanOrEqualTo(String value) {
            addCriterion("attribute2 <=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Like(String value) {
            addCriterion("attribute2 like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotLike(String value) {
            addCriterion("attribute2 not like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2In(List<String> values) {
            addCriterion("attribute2 in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotIn(List<String> values) {
            addCriterion("attribute2 not in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Between(String value1, String value2) {
            addCriterion("attribute2 between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotBetween(String value1, String value2) {
            addCriterion("attribute2 not between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNull() {
            addCriterion("attribute3 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNotNull() {
            addCriterion("attribute3 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute3EqualTo(String value) {
            addCriterion("attribute3 =", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotEqualTo(String value) {
            addCriterion("attribute3 <>", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThan(String value) {
            addCriterion("attribute3 >", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThanOrEqualTo(String value) {
            addCriterion("attribute3 >=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThan(String value) {
            addCriterion("attribute3 <", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThanOrEqualTo(String value) {
            addCriterion("attribute3 <=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Like(String value) {
            addCriterion("attribute3 like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotLike(String value) {
            addCriterion("attribute3 not like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3In(List<String> values) {
            addCriterion("attribute3 in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotIn(List<String> values) {
            addCriterion("attribute3 not in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Between(String value1, String value2) {
            addCriterion("attribute3 between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotBetween(String value1, String value2) {
            addCriterion("attribute3 not between", value1, value2, "attribute3");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table repay_record
     *
     * @mbggenerated do_not_delete_during_merge Wed Sep 20 15:13:39 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table repay_record
     *
     * @mbggenerated Wed Sep 20 15:13:39 CST 2017
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}