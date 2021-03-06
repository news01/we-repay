package com.we.repay.dao.repay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.po.repay.YearPayRecordCriteria;

public interface YearPayRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int countByExample(YearPayRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int deleteByExample(YearPayRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int insert(YearPayRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int insertSelective(YearPayRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    List<YearPayRecord> selectByExample(YearPayRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    YearPayRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int updateByExampleSelective(@Param("record") YearPayRecord record, @Param("example") YearPayRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int updateByExample(@Param("record") YearPayRecord record, @Param("example") YearPayRecordCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int updateByPrimaryKeySelective(YearPayRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table year_pay_record
     *
     * @mbggenerated Thu Nov 16 16:50:42 CST 2017
     */
    int updateByPrimaryKey(YearPayRecord record);
    
    /**
     * #############################################################
     */
    
    public List<YearPayRecord> selectListSelective(YearPayRecord record);
    
    /**
     * test
     */
    public List<YearPayRecord> selectAmountSum(YearPayRecord record);
    
    public int selectAmountSumCount(YearPayRecord record);
    
    public List<YearPayRecord> selectByIdList(@Param("ids")List<Long> ids);
    
}