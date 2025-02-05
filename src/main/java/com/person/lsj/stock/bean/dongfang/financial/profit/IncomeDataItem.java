package com.person.lsj.stock.bean.dongfang.financial.profit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IncomeDataItem {
    /**
     * 证券代码（带交易所后缀）
     */
    @JsonProperty("SECUCODE")
    private String secuCode;

    /**
     * 证券代码
     */
    @JsonProperty("SECURITY_CODE")
    private String securityCode;

    /**
     * 证券简称
     */
    @JsonProperty("SECURITY_NAME_ABBR")
    private String securityNameAbbr;

    /**
     * 机构代码
     */
    @JsonProperty("ORG_CODE")
    private String orgCode;

    /**
     * 机构类型
     */
    @JsonProperty("ORG_TYPE")
    private String orgType;

    /**
     * 报告日期
     */
    @JsonProperty("REPORT_DATE")
    private String reportDate;

    /**
     * 报告类型
     */
    @JsonProperty("REPORT_TYPE")
    private String reportType;

    /**
     * 报告日期名称
     */
    @JsonProperty("REPORT_DATE_NAME")
    private String reportDateName;

    /**
     * 证券类型代码
     */
    @JsonProperty("SECURITY_TYPE_CODE")
    private String securityTypeCode;

    /**
     * 公告日期
     */
    @JsonProperty("NOTICE_DATE")
    private String noticeDate;

    /**
     * 更新日期
     */
    @JsonProperty("UPDATE_DATE")
    private String updateDate;

    /**
     * 货币类型
     */
    @JsonProperty("CURRENCY")
    private String currency;

    /**
     * 营业总收入
     */
    @JsonProperty("TOTAL_OPERATE_INCOME")
    private Double totalOperateIncome;

    /**
     * 营业总收入同比增长率
     */
    @JsonProperty("TOTAL_OPERATE_INCOME_YOY")
    private Double totalOperateIncomeYoy;

    /**
     * 营业收入
     */
    @JsonProperty("OPERATE_INCOME")
    private Double operateIncome;

    /**
     * 营业收入同比增长率
     */
    @JsonProperty("OPERATE_INCOME_YOY")
    private Double operateIncomeYoy;

    /**
     * 利息收入
     */
    @JsonProperty("INTEREST_INCOME")
    private Double interestIncome;

    /**
     * 利息收入同比增长率
     */
    @JsonProperty("INTEREST_INCOME_YOY")
    private Double interestIncomeYoy;

    /**
     * 已赚保费
     */
    @JsonProperty("EARNED_PREMIUM")
    private Double earnedPremium;

    /**
     * 已赚保费同比增长率
     */
    @JsonProperty("EARNED_PREMIUM_YOY")
    private Double earnedPremiumYoy;

    /**
     * 手续费及佣金收入
     */
    @JsonProperty("FEE_COMMISSION_INCOME")
    private Double feeCommissionIncome;

    /**
     * 手续费及佣金收入同比增长率
     */
    @JsonProperty("FEE_COMMISSION_INCOME_YOY")
    private Double feeCommissionIncomeYoy;

    /**
     * 其他业务收入
     */
    @JsonProperty("OTHER_BUSINESS_INCOME")
    private Double otherBusinessIncome;

    /**
     * 其他业务收入同比增长率
     */
    @JsonProperty("OTHER_BUSINESS_INCOME_YOY")
    private Double otherBusinessIncomeYoy;

    /**
     * 营业总收入其他项
     */
    @JsonProperty("TOI_OTHER")
    private Double toiOther;

    /**
     * 营业总收入其他项同比增长率
     */
    @JsonProperty("TOI_OTHER_YOY")
    private Double toiOtherYoy;

    /**
     * 营业总成本
     */
    @JsonProperty("TOTAL_OPERATE_COST")
    private Double totalOperateCost;

    /**
     * 营业总成本同比增长率
     */
    @JsonProperty("TOTAL_OPERATE_COST_YOY")
    private Double totalOperateCostYoy;

    /**
     * 营业成本
     */
    @JsonProperty("OPERATE_COST")
    private Double operateCost;

    /**
     * 营业成本同比增长率
     */
    @JsonProperty("OPERATE_COST_YOY")
    private Double operateCostYoy;

    /**
     * 利息支出
     */
    @JsonProperty("INTEREST_EXPENSE")
    private Double interestExpense;

    /**
     * 利息支出同比增长率
     */
    @JsonProperty("INTEREST_EXPENSE_YOY")
    private Double interestExpenseYoy;

    /**
     * 手续费及佣金支出
     */
    @JsonProperty("FEE_COMMISSION_EXPENSE")
    private Double feeCommissionExpense;

    /**
     * 手续费及佣金支出同比增长率
     */
    @JsonProperty("FEE_COMMISSION_EXPENSE_YOY")
    private Double feeCommissionExpenseYoy;

    /**
     * 研发费用
     */
    @JsonProperty("RESEARCH_EXPENSE")
    private Double researchExpense;

    /**
     * 研发费用同比增长率
     */
    @JsonProperty("RESEARCH_EXPENSE_YOY")
    private Double researchExpenseYoy;

    /**
     * 退保金
     */
    @JsonProperty("SURRENDER_VALUE")
    private Double surrenderValue;

    /**
     * 退保金同比增长率
     */
    @JsonProperty("SURRENDER_VALUE_YOY")
    private Double surrenderValueYoy;

    /**
     * 赔付支出净额
     */
    @JsonProperty("NET_COMPENSATE_EXPENSE")
    private Double netCompensateExpense;

    /**
     * 赔付支出净额同比增长率
     */
    @JsonProperty("NET_COMPENSATE_EXPENSE_YOY")
    private Double netCompensateExpenseYoy;

    /**
     * 提取保险责任准备金净额
     */
    @JsonProperty("NET_CONTRACT_RESERVE")
    private Double netContractReserve;

    /**
     * 提取保险责任准备金净额同比增长率
     */
    @JsonProperty("NET_CONTRACT_RESERVE_YOY")
    private Double netContractReserveYoy;

    /**
     * 保单红利支出
     */
    @JsonProperty("POLICY_BONUS_EXPENSE")
    private Double policyBonusExpense;

    /**
     * 保单红利支出同比增长率
     */
    @JsonProperty("POLICY_BONUS_EXPENSE_YOY")
    private Double policyBonusExpenseYoy;

    /**
     * 分保费用
     */
    @JsonProperty("REINSURE_EXPENSE")
    private Double reinsureExpense;

    /**
     * 分保费用同比增长率
     */
    @JsonProperty("REINSURE_EXPENSE_YOY")
    private Double reinsureExpenseYoy;

    /**
     * 其他业务成本
     */
    @JsonProperty("OTHER_BUSINESS_COST")
    private Double otherBusinessCost;

    /**
     * 其他业务成本同比增长率
     */
    @JsonProperty("OTHER_BUSINESS_COST_YOY")
    private Double otherBusinessCostYoy;

    /**
     * 营业税金及附加
     */
    @JsonProperty("OPERATE_TAX_ADD")
    private Double operateTaxAdd;

    /**
     * 营业税金及附加同比增长率
     */
    @JsonProperty("OPERATE_TAX_ADD_YOY")
    private Double operateTaxAddYoy;

    /**
     * 销售费用
     */
    @JsonProperty("SALE_EXPENSE")
    private Double saleExpense;

    /**
     * 销售费用同比增长率
     */
    @JsonProperty("SALE_EXPENSE_YOY")
    private Double saleExpenseYoy;

    /**
     * 管理费用
     */
    @JsonProperty("MANAGE_EXPENSE")
    private Double manageExpense;

    /**
     * 管理费用同比增长率
     */
    @JsonProperty("MANAGE_EXPENSE_YOY")
    private Double manageExpenseYoy;

    /**
     * 管理费用中的研发费用
     */
    @JsonProperty("ME_RESEARCH_EXPENSE")
    private Double meResearchExpense;

    /**
     * 管理费用中的研发费用同比增长率
     */
    @JsonProperty("ME_RESEARCH_EXPENSE_YOY")
    private Double meResearchExpenseYoy;

    /**
     * 财务费用
     */
    @JsonProperty("FINANCE_EXPENSE")
    private Double financeExpense;

    /**
     * 财务费用同比增长率
     */
    @JsonProperty("FINANCE_EXPENSE_YOY")
    private Double financeExpenseYoy;

    /**
     * 财务费用中的利息支出
     */
    @JsonProperty("FE_INTEREST_EXPENSE")
    private Double feInterestExpense;

    /**
     * 财务费用中的利息支出同比增长率
     */
    @JsonProperty("FE_INTEREST_EXPENSE_YOY")
    private Double feInterestExpenseYoy;

    /**
     * 财务费用中的利息收入
     */
    @JsonProperty("FE_INTEREST_INCOME")
    private Double feInterestIncome;

    /**
     * 财务费用中的利息收入同比增长率
     */
    @JsonProperty("FE_INTEREST_INCOME_YOY")
    private Double feInterestIncomeYoy;

    /**
     * 资产减值损失
     */
    @JsonProperty("ASSET_IMPAIRMENT_LOSS")
    private Double assetImpairmentLoss;

    /**
     * 资产减值损失同比增长率
     */
    @JsonProperty("ASSET_IMPAIRMENT_LOSS_YOY")
    private Double assetImpairmentLossYoy;

    /**
     * 信用减值损失
     */
    @JsonProperty("CREDIT_IMPAIRMENT_LOSS")
    private Double creditImpairmentLoss;

    /**
     * 信用减值损失同比增长率
     */
    @JsonProperty("CREDIT_IMPAIRMENT_LOSS_YOY")
    private Double creditImpairmentLossYoy;

    /**
     * 营业总成本其他项
     */
    @JsonProperty("TOC_OTHER")
    private Double tocOther;

    /**
     * 营业总成本其他项同比增长率
     */
    @JsonProperty("TOC_OTHER_YOY")
    private Double tocOtherYoy;

    /**
     * 公允价值变动收益
     */
    @JsonProperty("FAIRVALUE_CHANGE_INCOME")
    private Double fairvalueChangeIncome;

    /**
     * 公允价值变动收益同比增长率
     */
    @JsonProperty("FAIRVALUE_CHANGE_INCOME_YOY")
    private Double fairvalueChangeIncomeYoy;

    /**
     * 投资收益
     */
    @JsonProperty("INVEST_INCOME")
    private Double investIncome;

    /**
     * 投资收益同比增长率
     */
    @JsonProperty("INVEST_INCOME_YOY")
    private Double investIncomeYoy;

    /**
     * 对联营企业和合营企业的投资收益
     */
    @JsonProperty("INVEST_JOINT_INCOME")
    private Double investJointIncome;

    /**
     * 对联营企业和合营企业的投资收益同比增长率
     */
    @JsonProperty("INVEST_JOINT_INCOME_YOY")
    private Double investJointIncomeYoy;

    /**
     * 净敞口套期收益
     */
    @JsonProperty("NET_EXPOSURE_INCOME")
    private Double netExposureIncome;

    /**
     * 净敞口套期收益同比增长率
     */
    @JsonProperty("NET_EXPOSURE_INCOME_YOY")
    private Double netExposureIncomeYoy;

    /**
     * 汇兑收益
     */
    @JsonProperty("EXCHANGE_INCOME")
    private Double exchangeIncome;

    /**
     * 汇兑收益同比增长率
     */
    @JsonProperty("EXCHANGE_INCOME_YOY")
    private Double exchangeIncomeYoy;

    /**
     * 资产处置收益
     */
    @JsonProperty("ASSET_DISPOSAL_INCOME")
    private Double assetDisposalIncome;

    /**
     * 资产处置收益同比增长率
     */
    @JsonProperty("ASSET_DISPOSAL_INCOME_YOY")
    private Double assetDisposalIncomeYoy;

    /**
     * 资产减值收益
     */
    @JsonProperty("ASSET_IMPAIRMENT_INCOME")
    private Double assetImpairmentIncome;

    /**
     * 资产减值收益同比增长率
     */
    @JsonProperty("ASSET_IMPAIRMENT_INCOME_YOY")
    private Double assetImpairmentIncomeYoy;

    /**
     * 信用减值收益
     */
    @JsonProperty("CREDIT_IMPAIRMENT_INCOME")
    private Double creditImpairmentIncome;

    /**
     * 信用减值收益同比增长率
     */
    @JsonProperty("CREDIT_IMPAIRMENT_INCOME_YOY")
    private Double creditImpairmentIncomeYoy;

    /**
     * 其他收益
     */
    @JsonProperty("OTHER_INCOME")
    private Double otherIncome;

    /**
     * 其他收益同比增长率
     */
    @JsonProperty("OTHER_INCOME_YOY")
    private Double otherIncomeYoy;

    /**
     * 营业利润其他项
     */
    @JsonProperty("OPERATE_PROFIT_OTHER")
    private Double operateProfitOther;

    /**
     * 营业利润其他项同比增长率
     */
    @JsonProperty("OPERATE_PROFIT_OTHER_YOY")
    private Double operateProfitOtherYoy;

    /**
     * 营业利润小计
     */
    @JsonProperty("OPERATE_PROFIT_BALANCE")
    private Double operateProfitBalance;

    /**
     * 营业利润小计同比增长率
     */
    @JsonProperty("OPERATE_PROFIT_BALANCE_YOY")
    private Double operateProfitBalanceYoy;

    /**
     * 营业利润
     */
    @JsonProperty("OPERATE_PROFIT")
    private Double operateProfit;

    /**
     * 营业利润同比增长率
     */
    @JsonProperty("OPERATE_PROFIT_YOY")
    private Double operateProfitYoy;

    /**
     * 营业外收入
     */
    @JsonProperty("NONBUSINESS_INCOME")
    private Double nonbusinessIncome;

    /**
     * 营业外收入同比增长率
     */
    @JsonProperty("NONBUSINESS_INCOME_YOY")
    private Double nonbusinessIncomeYoy;

    /**
     * 非流动资产处置利得
     */
    @JsonProperty("NONCURRENT_DISPOSAL_INCOME")
    private Double noncurrentDisposalIncome;

    /**
     * 非流动资产处置利得同比增长率
     */
    @JsonProperty("NONCURRENT_DISPOSAL_INCOME_YOY")
    private Double noncurrentDisposalIncomeYoy;

    /**
     * 营业外支出
     */
    @JsonProperty("NONBUSINESS_EXPENSE")
    private Double nonbusinessExpense;

    /**
     * 营业外支出同比增长率
     */
    @JsonProperty("NONBUSINESS_EXPENSE_YOY")
    private Double nonbusinessExpenseYoy;

    /**
     * 非流动资产处置损失
     */
    @JsonProperty("NONCURRENT_DISPOSAL_LOSS")
    private Double noncurrentDisposalLoss;

    /**
     * 非流动资产处置损失同比增长率
     */
    @JsonProperty("NONCURRENT_DISPOSAL_LOSS_YOY")
    private Double noncurrentDisposalLossYoy;

    /**
     * 利润总额其他项
     */
    @JsonProperty("EFFECT_TP_OTHER")
    private Double effectTpOther;

    /**
     * 利润总额其他项同比增长率
     */
    @JsonProperty("EFFECT_TP_OTHER_YOY")
    private Double effectTpOtherYoy;

    /**
     * 利润总额小计
     */
    @JsonProperty("TOTAL_PROFIT_BALANCE")
    private Double totalProfitBalance;

    /**
     * 利润总额小计同比增长率
     */
    @JsonProperty("TOTAL_PROFIT_BALANCE_YOY")
    private Double totalProfitBalanceY;

    /**
     * 利润总额
     */
    @JsonProperty("TOTAL_PROFIT")
    private Double totalProfit;

    /**
     * 利润总额同比增长率
     */
    @JsonProperty("TOTAL_PROFIT_YOY")
    private Double totalProfitYoy;

    /**
     * 所得税费用
     */
    @JsonProperty("INCOME_TAX")
    private Double incomeTax;

    /**
     * 所得税费用同比增长率
     */
    @JsonProperty("INCOME_TAX_YOY")
    private Double incomeTaxYoy;

    /**
     * 影响净利润的其他项目
     */
    @JsonProperty("EFFECT_NETPROFIT_OTHER")
    private Double effectNetProfitOther;

    /**
     * 影响净利润的其他项目同比增长率
     */
    @JsonProperty("EFFECT_NETPROFIT_OTHER_YOY")
    private Double effectNetProfitOtherYoy;

    /**
     * 影响净利润的小计
     */
    @JsonProperty("EFFECT_NETPROFIT_BALANCE")
    private Double effectNetProfitBalance;

    /**
     * 影响净利润的小计同比增长率
     */
    @JsonProperty("EFFECT_NETPROFIT_BALANCE_YOY")
    private Double effectNetProfitBalanceYoy;

    /**
     * 未确认投资损失
     */
    @JsonProperty("UNCONFIRM_INVEST_LOSS")
    private Double unconfirmInvestLoss;

    /**
     * 未确认投资损失同比增长率
     */
    @JsonProperty("UNCONFIRM_INVEST_LOSS_YOY")
    private Double unconfirmInvestLossYoy;

    /**
     * 净利润
     */
    @JsonProperty("NETPROFIT")
    private Double netProfit;

    /**
     * 净利润同比增长率
     */
    @JsonProperty("NETPROFIT_YOY")
    private Double netProfitYoy;

    /**
     * 合并前利润
     */
    @JsonProperty("PRECOMBINE_PROFIT")
    private Double preCombineProfit;

    /**
     * 合并前利润同比增长率
     */
    @JsonProperty("PRECOMBINE_PROFIT_YOY")
    private Double preCombineProfitYoy;

    /**
     * 持续经营净利润
     */
    @JsonProperty("CONTINUED_NETPROFIT")
    private Double continuedNetProfit;

    /**
     * 持续经营净利润同比增长率
     */
    @JsonProperty("CONTINUED_NETPROFIT_YOY")
    private Double continuedNetProfitYoy;

    /**
     * 终止经营净利润
     */
    @JsonProperty("DISCONTINUED_NETPROFIT")
    private Double discontinuedNetProfit;

    /**
     * 终止经营净利润同比增长率
     */
    @JsonProperty("DISCONTINUED_NETPROFIT_YOY")
    private Double discontinuedNetProfitYoy;

    /**
     * 归属于母公司股东的净利润
     */
    @JsonProperty("PARENT_NETPROFIT")
    private Double parentNetProfit;

    /**
     * 归属于母公司股东的净利润同比增长率
     */
    @JsonProperty("PARENT_NETPROFIT_YOY")
    private Double parentNetProfitYoy;

    /**
     * 少数股东损益
     */
    @JsonProperty("MINORITY_INTEREST")
    private Double minorityInterest;

    /**
     * 少数股东损益同比增长率
     */
    @JsonProperty("MINORITY_INTEREST_YOY")
    private Double minorityInterestYoy;

    /**
     * 扣除非经常性损益后归属于母公司股东的净利润
     */
    @JsonProperty("DEDUCT_PARENT_NETPROFIT")
    private Double deductParentNetProfit;

    /**
     * 扣除非经常性损益后归属于母公司股东的净利润同比增长率
     */
    @JsonProperty("DEDUCT_PARENT_NETPROFIT_YOY")
    private Double deductParentNetProfitYoy;

    /**
     * 净利润其他项目
     */
    @JsonProperty("NETPROFIT_OTHER")
    private Double netProfitOther;

    /**
     * 净利润其他项目同比增长率
     */
    @JsonProperty("NETPROFIT_OTHER_YOY")
    private Double netProfitOtherYoy;

    /**
     * 净利润小计
     */
    @JsonProperty("NETPROFIT_BALANCE")
    private Double netProfitBalance;

    /**
     * 净利润小计同比增长率
     */
    @JsonProperty("NETPROFIT_BALANCE_YOY")
    private Double netProfitBalanceYoy;

    /**
     * 基本每股收益
     */
    @JsonProperty("BASIC_EPS")
    private Double basicEps;

    /**
     * 基本每股收益同比增长率
     */
    @JsonProperty("BASIC_EPS_YOY")
    private Double basicEpsYoy;

    /**
     * 稀释每股收益
     */
    @JsonProperty("DILUTED_EPS")
    private Double dilutedEps;

    /**
     * 稀释每股收益同比增长率
     */
    @JsonProperty("DILUTED_EPS_YOY")
    private Double dilutedEpsYoy;

    /**
     * 其他综合收益
     */
    @JsonProperty("OTHER_COMPRE_INCOME")
    private Double otherCompreIncome;

    /**
     * 其他综合收益同比增长率
     */
    @JsonProperty("OTHER_COMPRE_INCOME_YOY")
    private Double otherCompreIncomeYoy;

    /**
     * 归属于母公司股东的其他综合收益
     */
    @JsonProperty("PARENT_OCI")
    private Double parentOci;

    /**
     * 归属于母公司股东的其他综合收益同比增长率
     */
    @JsonProperty("PARENT_OCI_YOY")
    private Double parentOciYoy;

    /**
     * 归属于少数股东的其他综合收益
     */
    @JsonProperty("MINORITY_OCI")
    private Double minorityOci;

    /**
     * 归属于少数股东的其他综合收益同比增长率
     */
    @JsonProperty("MINORITY_OCI_YOY")
    private Double minorityOciYoy;

    /**
     * 归属于母公司股东的其他综合收益其他项目
     */
    @JsonProperty("PARENT_OCI_OTHER")
    private Double parentOciOther;

    /**
     * 归属于母公司股东的其他综合收益其他项目同比增长率
     */
    @JsonProperty("PARENT_OCI_OTHER_YOY")
    private Double parentOciOtherYoy;

    /**
     * 归属于母公司股东的其他综合收益小计
     */
    @JsonProperty("PARENT_OCI_BALANCE")
    private Double parentOciBalance;

    /**
     * 归属于母公司股东的其他综合收益小计同比增长率
     */
    @JsonProperty("PARENT_OCI_BALANCE_YOY")
    private Double parentOciBalanceYoy;

    /**
     * 不能重分类进损益的其他综合收益
     */
    @JsonProperty("UNABLE_OCI")
    private Double unableOci;

    /**
     * 不能重分类进损益的其他综合收益同比增长率
     */
    @JsonProperty("UNABLE_OCI_YOY")
    private Double unableOciYoy;

    /**
     * 信用风险公允价值变动
     */
    @JsonProperty("CREDITRISK_FAIRVALUE_CHANGE")
    private Double creditRiskFairValueChange;

    /**
     * 信用风险公允价值变动同比增长率
     */
    @JsonProperty("CREDITRISK_FAIRVALUE_CHANGE_YOY")
    private Double creditRiskFairValueChangeYoy;

    /**
     * 其他权益工具投资公允价值变动
     */
    @JsonProperty("OTHERRIGHT_FAIRVALUE_CHANGE")
    private Double otherRightFairValueChange;

    /**
     * 其他权益工具投资公允价值变动同比增长率
     */
    @JsonProperty("OTHERRIGHT_FAIRVALUE_CHANGE_YOY")
    private Double otherRightFairValueChangeYoy;

    /**
     * 设定受益计划变动额
     */
    @JsonProperty("SETUP_PROFIT_CHANGE")
    private Double setupProfitChange;

    /**
     * 设定受益计划变动额同比增长率
     */
    @JsonProperty("SETUP_PROFIT_CHANGE_YOY")
    private Double setupProfitChangeYoy;

    /**
     * 其他不能重分类进损益的其他综合收益
     */
    @JsonProperty("RIGHTLAW_UNABLE_OCI")
    private Double rightLawUnableOci;

    /**
     * 其他不能重分类进损益的其他综合收益同比增长率
     */
    @JsonProperty("RIGHTLAW_UNABLE_OCI_YOY")
    private Double rightLawUnableOciYoy;

    /**
     * 不能重分类进损益的其他综合收益其他项目
     */
    @JsonProperty("UNABLE_OCI_OTHER")
    private Double unableOciOther;

    /**
     * 不能重分类进损益的其他综合收益其他项目同比增长率
     */
    @JsonProperty("UNABLE_OCI_OTHER_YOY")
    private Double unableOciOtherYoy;

    /**
     * 不能重分类进损益的其他综合收益小计
     */
    @JsonProperty("UNABLE_OCI_BALANCE")
    private Double unableOciBalance;

    /**
     * 不能重分类进损益的其他综合收益小计同比增长率
     */
    @JsonProperty("UNABLE_OCI_BALANCE_YOY")
    private Double unableOciBalanceYoy;

    /**
     * 能重分类进损益的其他综合收益
     */
    @JsonProperty("ABLE_OCI")
    private Double ableOci;

    /**
     * 能重分类进损益的其他综合收益同比增长率
     */
    @JsonProperty("ABLE_OCI_YOY")
    private Double ableOciYoy;

    /**
     * 其他能重分类进损益的其他综合收益
     */
    @JsonProperty("RIGHTLAW_ABLE_OCI")
    private Double rightLawAbleOci;

    /**
     * 其他能重分类进损益的其他综合收益同比增长率
     */
    @JsonProperty("RIGHTLAW_ABLE_OCI_YOY")
    private Double rightLawAbleOciYoy;

    /**
     * 以公允价值计量且其变动计入其他综合收益的金融资产公允价值变动
     */
    @JsonProperty("AFA_FAIRVALUE_CHANGE")
    private Double afaFairValueChange;

    /**
     * 以公允价值计量且其变动计入其他综合收益的金融资产公允价值变动同比增长率
     */
    @JsonProperty("AFA_FAIRVALUE_CHANGE_YOY")
    private Double afaFairValueChangeYoy;

    /**
     * 以公允价值计量且其变动计入其他综合收益的金融资产信用减值准备
     */
    @JsonProperty("HMI_AFA")
    private Double hmiAfa;

    /**
     * 以公允价值计量且其变动计入其他综合收益的金融资产信用减值准备同比增长率
     */
    @JsonProperty("HMI_AFA_YOY")
    private Double hmiAfaYoy;

    /**
     * 现金流量套期储备
     */
    @JsonProperty("CASHFLOW_HEDGE_VALID")
    private Double cashFlowHedgeValid;

    /**
     * 现金流量套期储备同比增长率
     */
    @JsonProperty("CASHFLOW_HEDGE_VALID_YOY")
    private Double cashFlowHedgeValidYoy;

    /**
     * 金融资产重分类计入其他综合收益的金额
     */
    @JsonProperty("CREDITOR_FAIRVALUE_CHANGE")
    private Double creditorFairValueChange;

    /**
     * 金融资产重分类计入其他综合收益的金额同比增长率
     */
    @JsonProperty("CREDITOR_FAIRVALUE_CHANGE_YOY")
    private Double creditorFairValueChangeYoy;

    /**
     * 金融资产减值准备
     */
    @JsonProperty("CREDITOR_IMPAIRMENT_RESERVE")
    private Double creditorImpairmentReserve;

    /**
     * 金融资产减值准备同比增长率
     */
    @JsonProperty("CREDITOR_IMPAIRMENT_RESERVE_YOY")
    private Double creditorImpairmentReserveYoy;

    /**
     * 其他债权投资公允价值变动
     */
    @JsonProperty("FINANCE_OCI_AMT")
    private Double financeOciAmt;

    /**
     * 其他债权投资公允价值变动同比增长率
     */
    @JsonProperty("FINANCE_OCI_AMT_YOY")
    private Double financeOciAmtYoy;

    /**
     * 外币财务报表折算差额
     */
    @JsonProperty("CONVERT_DIFF")
    private Double convertDiff;

    /**
     * 外币财务报表折算差额同比增长率
     */
    @JsonProperty("CONVERT_DIFF_YOY")
    private Double convertDiffYoy;

    /**
     * 能重分类进损益的其他综合收益其他项目
     */
    @JsonProperty("ABLE_OCI_OTHER")
    private Double ableOciOther;

    /**
     * 能重分类进损益的其他综合收益其他项目同比增长率
     */
    @JsonProperty("ABLE_OCI_OTHER_YOY")
    private Double ableOciOtherYoy;

    /**
     * 能重分类进损益的其他综合收益小计
     */
    @JsonProperty("ABLE_OCI_BALANCE")
    private Double ableOciBalance;

    /**
     * 能重分类进损益的其他综合收益小计同比增长率
     */
    @JsonProperty("ABLE_OCI_BALANCE_YOY")
    private Double ableOciBalanceYoy;

    /**
     * 其他综合收益其他项目
     */
    @JsonProperty("OCI_OTHER")
    private Double ociOther;

    /**
     * 其他综合收益其他项目同比增长率
     */
    @JsonProperty("OCI_OTHER_YOY")
    private Double ociOtherYoy;

    /**
     * 其他综合收益小计
     */
    @JsonProperty("OCI_BALANCE")
    private Double ociBalance;

    /**
     * 其他综合收益小计同比增长率
     */
    @JsonProperty("OCI_BALANCE_YOY")
    private Double ociBalanceYoy;

    /**
     * 综合收益总额
     */
    @JsonProperty("TOTAL_COMPRE_INCOME")
    private Double totalCompreIncome;

    /**
     * 综合收益总额同比增长率
     */
    @JsonProperty("TOTAL_COMPRE_INCOME_YOY")
    private Double totalCompreIncomeYoy;

    /**
     * 归属于母公司所有者的综合收益总额
     */
    @JsonProperty("PARENT_TCI")
    private Double parentTci;

    /**
     * 归属于母公司所有者的综合收益总额同比增长率
     */
    @JsonProperty("PARENT_TCI_YOY")
    private Double parentTciYoy;

    /**
     * 归属于少数股东的综合收益总额
     */
    @JsonProperty("MINORITY_TCI")
    private Double minorityTci;

    /**
     * 归属于少数股东的综合收益总额同比增长率
     */
    @JsonProperty("MINORITY_TCI_YOY")
    private Double minorityTciYoy;

    /**
     * 合并前综合收益总额
     */
    @JsonProperty("PRECOMBINE_TCI")
    private Double preCombineTci;

    /**
     * 合并前综合收益总额同比增长率
     */
    @JsonProperty("PRECOMBINE_TCI_YOY")
    private Double preCombineTciYoy;

    /**
     * 影响综合收益总额的小计
     */
    @JsonProperty("EFFECT_TCI_BALANCE")
    private Double effectTciBalance;

    /**
     * 影响综合收益总额的小计同比增长率
     */
    @JsonProperty("EFFECT_TCI_BALANCE_YOY")
    private Double effectTciBalanceYoy;

    /**
     * 综合收益总额的其他项目
     */
    @JsonProperty("TCI_OTHER")
    private Double tciOther;

    /**
     * 综合收益总额的其他项目同比增长率
     */
    @JsonProperty("TCI_OTHER_YOY")
    private Double tciOtherYoy;

    /**
     * 综合收益总额小计
     */
    @JsonProperty("TCI_BALANCE")
    private Double tciBalance;

    /**
     * 综合收益总额小计同比增长率
     */
    @JsonProperty("TCI_BALANCE_YOY")
    private Double tciBalanceYoy;

    /**
     * 经营活动现金流量净额期末余额
     */
    @JsonProperty("ACF_END_INCOME")
    private Double acfEndIncome;

    /**
     * 经营活动现金流量净额期末余额同比增长率
     */
    @JsonProperty("ACF_END_INCOME_YOY")
    private Double acfEndIncomeYoy;

    /**
     * 审计意见类型
     */
    @JsonProperty("OPINION_TYPE")
    private String opinionType;
}
