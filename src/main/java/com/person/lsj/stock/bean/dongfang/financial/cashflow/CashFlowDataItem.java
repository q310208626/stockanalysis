package com.person.lsj.stock.bean.dongfang.financial.cashflow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CashFlowDataItem {

    @JsonProperty("SECUCODE")
    private String secucode; // 证券代码

    @JsonProperty("SECURITY_CODE")
    private String securityCode; // 证券代码

    @JsonProperty("SECURITY_NAME_ABBR")
    private String securityNameAbbr; // 证券简称

    @JsonProperty("ORG_CODE")
    private String orgCode; // 组织机构代码

    @JsonProperty("ORG_TYPE")
    private String orgType; // 机构类型

    @JsonProperty("REPORT_DATE")
    private String reportDate; // 报告日期

    @JsonProperty("REPORT_TYPE")
    private String reportType; // 报告类型

    @JsonProperty("REPORT_DATE_NAME")
    private String reportDateName; // 报告日期名称

    @JsonProperty("SECURITY_TYPE_CODE")
    private String securityTypeCode; // 证券类型代码

    @JsonProperty("NOTICE_DATE")
    private String noticeDate; // 公告日期

    @JsonProperty("UPDATE_DATE")
    private String updateDate; // 更新日期

    @JsonProperty("CURRENCY")
    private String currency; // 货币类型

    @JsonProperty("SALES_SERVICES")
    private Double salesServices; // 销售服务收入

    @JsonProperty("DEPOSIT_INTERBANK_ADD")
    private Double depositInterbankAdd; // 存放同业增加

    @JsonProperty("LOAN_PBC_ADD")
    private Double loanPbcAdd; // 贷款增加

    @JsonProperty("OFI_BF_ADD")
    private Double ofiBfAdd; // 其他金融资产增加

    @JsonProperty("RECEIVE_ORIGIC_PREMIUM")
    private Double receiveOrigicPremium; // 收到原保险保费

    @JsonProperty("RECEIVE_REINSURE_NET")
    private Double receiveReinsureNet; // 收到再保险净额

    @JsonProperty("INSURED_INVEST_ADD")
    private Double insuredInvestAdd; // 保险投资增加

    @JsonProperty("DISPOSAL_TFA_ADD")
    private Double disposalTfaAdd; // 处置交易性金融资产增加

    @JsonProperty("RECEIVE_INTEREST_COMMISSION")
    private Double receiveInterestCommission; // 收到利息和佣金

    @JsonProperty("BORROW_FUND_ADD")
    private Double borrowFundAdd; // 借入资金增加

    @JsonProperty("LOAN_ADVANCE_REDUCE")
    private Double loanAdvanceReduce; // 贷款和垫款减少

    @JsonProperty("REPO_BUSINESS_ADD")
    private Double repoBusinessAdd; // 回购业务增加

    @JsonProperty("RECEIVE_TAX_REFUND")
    private Double receiveTaxRefund; // 收到税收返还

    @JsonProperty("RECEIVE_OTHER_OPERATE")
    private Double receiveOtherOperate; // 收到其他与经营活动有关的现金

    @JsonProperty("OPERATE_INFLOW_OTHER")
    private Double operateInflowOther; // 其他经营活动现金流入

    @JsonProperty("OPERATE_INFLOW_BALANCE")
    private Double operateInflowBalance; // 经营活动现金流入余额

    @JsonProperty("TOTAL_OPERATE_INFLOW")
    private Double totalOperateInflow; // 经营活动现金流入总额

    @JsonProperty("BUY_SERVICES")
    private Double buyServices; // 购买商品、接受劳务支付的现金

    @JsonProperty("LOAN_ADVANCE_ADD")
    private Double loanAdvanceAdd; // 贷款和垫款增加

    @JsonProperty("PBC_INTERBANK_ADD")
    private Double pbcInterbankAdd; // 存放中央银行和同业款项增加

    @JsonProperty("PAY_ORIGIC_COMPENSATE")
    private Double payOrigicCompensate; // 支付原保险赔付

    @JsonProperty("PAY_INTEREST_COMMISSION")
    private Double payInterestCommission; // 支付利息和佣金

    @JsonProperty("PAY_POLICY_BONUS")
    private Double payPolicyBonus; // 支付保单红利

    @JsonProperty("PAY_STAFF_CASH")
    private Double payStaffCash; // 支付给职工以及为职工支付的现金

    @JsonProperty("PAY_ALL_TAX")
    private Double payAllTax; // 支付的各项税费

    @JsonProperty("PAY_OTHER_OPERATE")
    private Double payOtherOperate; // 支付其他与经营活动有关的现金

    @JsonProperty("OPERATE_OUTFLOW_OTHER")
    private Double operateOutflowOther; // 其他经营活动现金流出

    @JsonProperty("OPERATE_OUTFLOW_BALANCE")
    private Double operateOutflowBalance; // 经营活动现金流出余额

    @JsonProperty("TOTAL_OPERATE_OUTFLOW")
    private Double totalOperateOutflow; // 经营活动现金流出总额

    @JsonProperty("OPERATE_NETCASH_OTHER")
    private Double operateNetcashOther; // 其他经营活动现金流量净额

    @JsonProperty("OPERATE_NETCASH_BALANCE")
    private Double operateNetcashBalance; // 经营活动现金流量净额余额

    @JsonProperty("NETCASH_OPERATE")
    private Double netcashOperate; // 经营活动产生的现金流量净额

    @JsonProperty("WITHDRAW_INVEST")
    private Double withdrawInvest; // 收回投资收到的现金

    @JsonProperty("RECEIVE_INVEST_INCOME")
    private Double receiveInvestIncome; // 取得投资收益收到的现金

    @JsonProperty("DISPOSAL_LONG_ASSET")
    private Double disposalLongAsset; // 处置长期资产收到的现金

    @JsonProperty("DISPOSAL_SUBSIDIARY_OTHER")
    private Double disposalSubsidiaryOther; // 处置子公司及其他营业单位收到的现金

    @JsonProperty("REDUCE_PLEDGE_TIMEDEPOSITS")
    private Double reducePledgeTimedeposits; // 减少质押定期存款

    @JsonProperty("RECEIVE_OTHER_INVEST")
    private Double receiveOtherInvest; // 收到其他与投资活动有关的现金

    @JsonProperty("INVEST_INFLOW_OTHER")
    private Double investInflowOther; // 其他投资活动现金流入

    @JsonProperty("INVEST_INFLOW_BALANCE")
    private Double investInflowBalance; // 投资活动现金流入余额

    @JsonProperty("TOTAL_INVEST_INFLOW")
    private Double totalInvestInflow; // 投资活动现金流入总额

    @JsonProperty("CONSTRUCT_LONG_ASSET")
    private Double constructLongAsset; // 购建长期资产支付的现金

    @JsonProperty("INVEST_PAY_CASH")
    private Double investPayCash; // 投资支付的现金

    @JsonProperty("PLEDGE_LOAN_ADD")
    private Double pledgeLoanAdd; // 质押贷款增加

    @JsonProperty("OBTAIN_SUBSIDIARY_OTHER")
    private Double obtainSubsidiaryOther; // 取得子公司及其他营业单位支付的现金

    @JsonProperty("ADD_PLEDGE_TIMEDEPOSITS")
    private Double addPledgeTimedeposits; // 增加质押定期存款

    @JsonProperty("PAY_OTHER_INVEST")
    private Double payOtherInvest; // 支付其他与投资活动有关的现金

    @JsonProperty("INVEST_OUTFLOW_OTHER")
    private Double investOutflowOther; // 其他投资活动现金流出

    @JsonProperty("INVEST_OUTFLOW_BALANCE")
    private Double investOutflowBalance; // 投资活动现金流出余额

    @JsonProperty("TOTAL_INVEST_OUTFLOW")
    private Double totalInvestOutflow; // 投资活动现金流出总额

    @JsonProperty("INVEST_NETCASH_OTHER")
    private Double investNetcashOther; // 其他投资活动现金流量净额

    @JsonProperty("INVEST_NETCASH_BALANCE")
    private Double investNetcashBalance; // 投资活动现金流量净额余额

    @JsonProperty("NETCASH_INVEST")
    private Double netcashInvest; // 投资活动产生的现金流量净额

    @JsonProperty("ACCEPT_INVEST_CASH")
    private Double acceptInvestCash; // 吸收投资收到的现金

    @JsonProperty("SUBSIDIARY_ACCEPT_INVEST")
    private Double subsidiaryAcceptInvest; // 子公司吸收投资收到的现金

    @JsonProperty("RECEIVE_LOAN_CASH")
    private Double receiveLoanCash; // 取得借款收到的现金

    @JsonProperty("ISSUE_BOND")
    private Double issueBond; // 发行债券收到的现金

    @JsonProperty("RECEIVE_OTHER_FINANCE")
    private Double receiveOtherFinance; // 收到其他与筹资活动有关的现金

    @JsonProperty("FINANCE_INFLOW_OTHER")
    private Double financeInflowOther; // 其他筹资活动现金流入

    @JsonProperty("FINANCE_INFLOW_BALANCE")
    private Double financeInflowBalance; // 筹资活动现金流入余额

    @JsonProperty("TOTAL_FINANCE_INFLOW")
    private Double totalFinanceInflow; // 筹资活动现金流入总额

    @JsonProperty("PAY_DEBT_CASH")
    private Double payDebtCash; // 偿还债务支付的现金

    @JsonProperty("ASSIGN_DIVIDEND_PORFIT")
    private Double assignDividendPorfit; // 分配股利、利润或偿付利息支付的现金

    @JsonProperty("SUBSIDIARY_PAY_DIVIDEND")
    private Double subsidiaryPayDividend; // 子公司支付股利、利润或偿付利息支付的现金

    @JsonProperty("BUY_SUBSIDIARY_EQUITY")
    private Double buySubsidiaryEquity; // 购买子公司股权支付的现金

    @JsonProperty("PAY_OTHER_FINANCE")
    private Double payOtherFinance; // 支付其他与筹资活动有关的现金

    @JsonProperty("SUBSIDIARY_REDUCE_CASH")
    private Double subsidiaryReduceCash; // 子公司减少现金

    @JsonProperty("FINANCE_OUTFLOW_OTHER")
    private Double financeOutflowOther; // 其他筹资活动现金流出

    @JsonProperty("FINANCE_OUTFLOW_BALANCE")
    private Double financeOutflowBalance; // 筹资活动现金流出余额

    @JsonProperty("TOTAL_FINANCE_OUTFLOW")
    private Double totalFinanceOutflow; // 筹资活动现金流出总额

    @JsonProperty("FINANCE_NETCASH_OTHER")
    private Double financeNetcashOther; // 其他筹资活动现金流量净额

    @JsonProperty("FINANCE_NETCASH_BALANCE")
    private Double financeNetcashBalance; // 筹资活动现金流量净额余额

    @JsonProperty("NETCASH_FINANCE")
    private Double netcashFinance; // 筹资活动产生的现金流量净额

    @JsonProperty("RATE_CHANGE_EFFECT")
    private Double rateChangeEffect; // 汇率变动对现金的影响

    @JsonProperty("CCE_ADD_OTHER")
    private Double cceAddOther; // 其他现金及现金等价物增加

    @JsonProperty("CCE_ADD_BALANCE")
    private Double cceAddBalance; // 现金及现金等价物增加余额

    @JsonProperty("CCE_ADD")
    private Double cceAdd; // 现金及现金等价物增加额

    @JsonProperty("BEGIN_CCE")
    private Double beginCce; // 期初现金及现金等价物余额

    @JsonProperty("END_CCE_OTHER")
    private Double endCceOther; // 期末其他现金及现金等价物

    @JsonProperty("END_CCE_BALANCE")
    private Double endCceBalance; // 期末现金及现金等价物余额

    @JsonProperty("END_CCE")
    private Double endCce; // 期末现金及现金等价物

    @JsonProperty("NETPROFIT")
    private Double netprofit; // 净利润

    @JsonProperty("ASSET_IMPAIRMENT")
    private Double assetImpairment; // 资产减值损失

    @JsonProperty("FA_IR_DEPR")
    private Double faIrDepr; // 固定资产折旧、油气资产折耗、生产性生物资产折旧

    @JsonProperty("OILGAS_BIOLOGY_DEPR")
    private Double oilgasBiologyDepr; // 油气资产折耗、生产性生物资产折旧

    @JsonProperty("IR_DEPR")
    private Double irDepr; // 无形资产摊销

    @JsonProperty("IA_AMORTIZE")
    private Double iaAmortize; // 长期待摊费用摊销

    @JsonProperty("LPE_AMORTIZE")
    private Double lpeAmortize; // 长期待摊费用摊销

    @JsonProperty("DEFER_INCOME_AMORTIZE")
    private Double deferIncomeAmortize; // 递延收益摊销

    @JsonProperty("PREPAID_EXPENSE_REDUCE")
    private Double prepaidExpenseReduce; // 预付费用减少

    @JsonProperty("ACCRUED_EXPENSE_ADD")
    private Double accruedExpenseAdd; // 预提费用增加

    @JsonProperty("DISPOSAL_LONGASSET_LOSS")
    private Double disposalLongassetLoss; // 处置长期资产损失

    @JsonProperty("FA_SCRAP_LOSS")
    private Double faScrapLoss; // 固定资产报废损失

    @JsonProperty("FAIRVALUE_CHANGE_LOSS")
    private Double fairvalueChangeLoss; // 公允价值变动损失

    @JsonProperty("FINANCE_EXPENSE")
    private Double financeExpense; // 财务费用

    @JsonProperty("INVEST_LOSS")
    private Double investLoss; // 投资损失

    @JsonProperty("DEFER_TAX")
    private Double deferTax; // 递延所得税

    @JsonProperty("DT_ASSET_REDUCE")
    private Double dtAssetReduce; // 递延所得税资产减少

    @JsonProperty("DT_LIAB_ADD")
    private Double dtLiabAdd; // 递延所得税负债增加

    @JsonProperty("PREDICT_LIAB_ADD")
    private Double predictLiabAdd; // 预计负债增加

    @JsonProperty("INVENTORY_REDUCE")
    private Double inventoryReduce; // 存货减少

    @JsonProperty("OPERATE_RECE_REDUCE")
    private Double operateReceReduce; // 经营性应收项目减少

    @JsonProperty("OPERATE_PAYABLE_ADD")
    private Double operatePayableAdd; // 经营性应付项目增加

    @JsonProperty("OTHER")
    private Double other; // 其他

    @JsonProperty("OPERATE_NETCASH_OTHERNOTE")
    private Double operateNetcashOthernote; // 其他经营活动现金流量净额备注

    @JsonProperty("OPERATE_NETCASH_BALANCENOTE")
    private Double operateNetcashBalancenote; // 经营活动现金流量净额余额备注

    @JsonProperty("NETCASH_OPERATENOTE")
    private Double netcashOperatenote; // 经营活动产生的现金流量净额备注

    @JsonProperty("DEBT_TRANSFER_CAPITAL")
    private Double debtTransferCapital; // 债务转为资本

    @JsonProperty("CONVERT_BOND_1YEAR")
    private Double convertBond1year; // 一年内到期的可转换债券

    @JsonProperty("FINLEASE_OBTAIN_FA")
    private Double finleaseObtainFa; // 融资租赁取得的固定资产

    @JsonProperty("UNINVOLVE_INVESTFIN_OTHER")
    private Double uninvolveInvestfinOther; // 不涉及投资和融资的其他

    @JsonProperty("END_CASH")
    private Double endCash; // 期末现金

    @JsonProperty("BEGIN_CASH")
    private Double beginCash; // 期初现金

    @JsonProperty("END_CASH_EQUIVALENTS")
    private Double endCashEquivalents; // 期末现金等价物

    @JsonProperty("BEGIN_CASH_EQUIVALENTS")
    private Double beginCashEquivalents; // 期初现金等价物

    @JsonProperty("CCE_ADD_OTHERNOTE")
    private Double cceAddOthernote; // 其他现金及现金等价物增加备注

    @JsonProperty("CCE_ADD_BALANCENOTE")
    private Double cceAddBalancenote; // 现金及现金等价物增加余额备注

    @JsonProperty("CCE_ADDNOTE")
    private Double cceAddnote; // 现金及现金等价物增加额备注

    @JsonProperty("SALES_SERVICES_YOY")
    private Double salesServicesYoy; // 销售服务收入同比增长率

    @JsonProperty("DEPOSIT_INTERBANK_ADD_YOY")
    private Double depositInterbankAddYoy; // 存放同业增加同比增长率

    @JsonProperty("LOAN_PBC_ADD_YOY")
    private Double loanPbcAddYoy; // 贷款增加同比增长率

    @JsonProperty("OFI_BF_ADD_YOY")
    private Double ofiBfAddYoy; // 其他金融资产增加同比增长率

    @JsonProperty("RECEIVE_ORIGIC_PREMIUM_YOY")
    private Double receiveOrigicPremiumYoy; // 收到原保险保费同比增长率

    @JsonProperty("RECEIVE_REINSURE_NET_YOY")
    private Double receiveReinsureNetYoy; // 收到再保险净额同比增长率

    @JsonProperty("INSURED_INVEST_ADD_YOY")
    private Double insuredInvestAddYoy; // 保险投资增加同比增长率

    /**
     * 处置交易性金融资产净增加额同比
     */
    @JsonProperty("DISPOSAL_TFA_ADD_YOY")
    private Double disposalTfaAddYoy;

    /**
     * 收到的利息、手续费及佣金净增加额同比
     */
    @JsonProperty("RECEIVE_INTEREST_COMMISSION_YOY")
    private Double receiveInterestCommissionYoy;

    /**
     * 拆入资金净增加额同比
     */
    @JsonProperty("BORROW_FUND_ADD_YOY")
    private Double borrowFundAddYoy;

    /**
     * 发放贷款及垫款净减少额同比
     */
    @JsonProperty("LOAN_ADVANCE_REDUCE_YOY")
    private Double loanAdvanceReduceYoy;

    /**
     * 回购业务资金净增加额同比
     */
    @JsonProperty("REPO_BUSINESS_ADD_YOY")
    private Double repoBusinessAddYoy;

    /**
     * 收到的税费返还同比
     */
    @JsonProperty("RECEIVE_TAX_REFUND_YOY")
    private Double receiveTaxRefundYoy;

    /**
     * 收到的其他与经营活动有关的现金同比
     */
    @JsonProperty("RECEIVE_OTHER_OPERATE_YOY")
    private Double receiveOtherOperateYoy;

    /**
     * 经营活动现金流入其他项目同比
     */
    @JsonProperty("OPERATE_INFLOW_OTHER_YOY")
    private Double operateInflowOtherYoy;

    /**
     * 经营活动现金流入小计同比
     */
    @JsonProperty("OPERATE_INFLOW_BALANCE_YOY")
    private Double operateInflowBalanceYoy;

    /**
     * 经营活动现金流入总额同比
     */
    @JsonProperty("TOTAL_OPERATE_INFLOW_YOY")
    private Double totalOperateInflowYoy;

    /**
     * 购买商品、接受劳务支付的现金同比
     */
    @JsonProperty("BUY_SERVICES_YOY")
    private Double buyServicesYoy;

    /**
     * 发放贷款及垫款净增加额同比
     */
    @JsonProperty("LOAN_ADVANCE_ADD_YOY")
    private Double loanAdvanceAddYoy;

    /**
     * 存放中央银行和同业款项净增加额同比
     */
    @JsonProperty("PBC_INTERBANK_ADD_YOY")
    private Double pbcInterbankAddYoy;

    /**
     * 支付原保险合同赔付款项的现金同比
     */
    @JsonProperty("PAY_ORIGIC_COMPENSATE_YOY")
    private Double payOrigicCompensateYoy;

    /**
     * 支付利息、手续费及佣金的现金同比
     */
    @JsonProperty("PAY_INTEREST_COMMISSION_YOY")
    private Double payInterestCommissionYoy;

    /**
     * 支付保单红利的现金同比
     */
    @JsonProperty("PAY_POLICY_BONUS_YOY")
    private Double payPolicyBonusYoy;

    /**
     * 支付给职工以及为职工支付的现金同比
     */
    @JsonProperty("PAY_STAFF_CASH_YOY")
    private Double payStaffCashYoy;

    /**
     * 支付的各项税费同比
     */
    @JsonProperty("PAY_ALL_TAX_YOY")
    private Double payAllTaxYoy;

    /**
     * 支付的其他与经营活动有关的现金同比
     */
    @JsonProperty("PAY_OTHER_OPERATE_YOY")
    private Double payOtherOperateYoy;

    /**
     * 经营活动现金流出其他项目同比
     */
    @JsonProperty("OPERATE_OUTFLOW_OTHER_YOY")
    private Double operateOutflowOtherYoy;

    /**
     * 经营活动现金流出小计同比
     */
    @JsonProperty("OPERATE_OUTFLOW_BALANCE_YOY")
    private Double operateOutflowBalanceYoy;

    /**
     * 经营活动现金流出总额同比
     */
    @JsonProperty("TOTAL_OPERATE_OUTFLOW_YOY")
    private Double totalOperateOutflowYoy;

    /**
     * 经营活动产生的现金流量净额其他项目同比
     */
    @JsonProperty("OPERATE_NETCASH_OTHER_YOY")
    private Double operateNetcashOtherYoy;

    /**
     * 经营活动产生的现金流量净额小计同比
     */
    @JsonProperty("OPERATE_NETCASH_BALANCE_YOY")
    private Double operateNetcashBalanceYoy;

    /**
     * 经营活动产生的现金流量净额同比
     */
    @JsonProperty("NETCASH_OPERATE_YOY")
    private Double netcashOperateYoy;

    /**
     * 收回投资收到的现金同比
     */
    @JsonProperty("WITHDRAW_INVEST_YOY")
    private Double withdrawInvestYoy;

    /**
     * 取得投资收益收到的现金同比
     */
    @JsonProperty("RECEIVE_INVEST_INCOME_YOY")
    private Double receiveInvestIncomeYoy;

    /**
     * 处置固定资产、无形资产和其他长期资产收回的现金净额同比
     */
    @JsonProperty("DISPOSAL_LONG_ASSET_YOY")
    private Double disposalLongAssetYoy;

    /**
     * 处置子公司及其他营业单位收到的现金净额同比
     */
    @JsonProperty("DISPOSAL_SUBSIDIARY_OTHER_YOY")
    private Double disposalSubsidiaryOtherYoy;

    /**
     * 减少质押和定期存款同比
     */
    @JsonProperty("REDUCE_PLEDGE_TIMEDEPOSITS_YOY")
    private Double reducePledgeTimedepositsYoy;

    /**
     * 收到的其他与投资活动有关的现金同比
     */
    @JsonProperty("RECEIVE_OTHER_INVEST_YOY")
    private Double receiveOtherInvestYoy;

    /**
     * 投资活动现金流入其他项目同比
     */
    @JsonProperty("INVEST_INFLOW_OTHER_YOY")
    private Double investInflowOtherYoy;

    /**
     * 投资活动现金流入小计同比
     */
    @JsonProperty("INVEST_INFLOW_BALANCE_YOY")
    private Double investInflowBalanceYoy;

    /**
     * 投资活动现金流入总额同比
     */
    @JsonProperty("TOTAL_INVEST_INFLOW_YOY")
    private Double totalInvestInflowYoy;

    /**
     * 购建固定资产、无形资产和其他长期资产支付的现金同比
     */
    @JsonProperty("CONSTRUCT_LONG_ASSET_YOY")
    private Double constructLongAssetYoy;

    /**
     * 投资支付的现金同比
     */
    @JsonProperty("INVEST_PAY_CASH_YOY")
    private Double investPayCashYoy;

    /**
     * 质押贷款净增加额同比
     */
    @JsonProperty("PLEDGE_LOAN_ADD_YOY")
    private Double pledgeLoanAddYoy;

    /**
     * 取得子公司及其他营业单位支付的现金净额同比
     */
    @JsonProperty("OBTAIN_SUBSIDIARY_OTHER_YOY")
    private Double obtainSubsidiaryOtherYoy;

    /**
     * 增加质押和定期存款同比
     */
    @JsonProperty("ADD_PLEDGE_TIMEDEPOSITS_YOY")
    private Double addPledgeTimedepositsYoy;

    /**
     * 支付的其他与投资活动有关的现金同比
     */
    @JsonProperty("PAY_OTHER_INVEST_YOY")
    private Double payOtherInvestYoy;

    /**
     * 投资活动现金流出其他项目同比
     */
    @JsonProperty("INVEST_OUTFLOW_OTHER_YOY")
    private Double investOutflowOtherYoy;

    /**
     * 投资活动现金流出小计同比
     */
    @JsonProperty("INVEST_OUTFLOW_BALANCE_YOY")
    private Double investOutflowBalanceYoy;

    /**
     * 投资活动现金流出总额同比
     */
    @JsonProperty("TOTAL_INVEST_OUTFLOW_YOY")
    private Double totalInvestOutflowYoy;

    /**
     * 投资活动产生的现金流量净额其他项目同比
     */
    @JsonProperty("INVEST_NETCASH_OTHER_YOY")
    private Double investNetcashOtherYoy;

    /**
     * 投资活动产生的现金流量净额小计同比
     */
    @JsonProperty("INVEST_NETCASH_BALANCE_YOY")
    private Double investNetcashBalanceYoy;

    /**
     * 投资活动产生的现金流量净额同比
     */
    @JsonProperty("NETCASH_INVEST_YOY")
    private Double netcashInvestYoy;

    /**
     * 吸收投资收到的现金同比
     */
    @JsonProperty("ACCEPT_INVEST_CASH_YOY")
    private Double acceptInvestCashYoy;

    /**
     * 子公司吸收少数股东投资收到的现金同比
     */
    @JsonProperty("SUBSIDIARY_ACCEPT_INVEST_YOY")
    private Double subsidiaryAcceptInvestYoy;

    /**
     * 取得借款收到的现金同比
     */
    @JsonProperty("RECEIVE_LOAN_CASH_YOY")
    private Double receiveLoanCashYoy;

    /**
     * 发行债券收到的现金同比
     */
    @JsonProperty("ISSUE_BOND_YOY")
    private Double issueBondYoy;

    /**
     * 收到的其他与筹资活动有关的现金同比
     */
    @JsonProperty("RECEIVE_OTHER_FINANCE_YOY")
    private Double receiveOtherFinanceYoy;

    /**
     * 筹资活动现金流入其他项目同比
     */
    @JsonProperty("FINANCE_INFLOW_OTHER_YOY")
    private Double financeInflowOtherYoy;

    /**
     * 筹资活动现金流入小计同比
     */
    @JsonProperty("FINANCE_INFLOW_BALANCE_YOY")
    private Double financeInflowBalanceYoy;

    /**
     * 筹资活动现金流入总额同比
     */
    @JsonProperty("TOTAL_FINANCE_INFLOW_YOY")
    private Double totalFinanceInflowYoy;

    /**
     * 偿还债务支付的现金同比
     */
    @JsonProperty("PAY_DEBT_CASH_YOY")
    private Double payDebtCashYoy;

    /**
     * 分配股利、利润或偿付利息支付的现金同比
     */
    @JsonProperty("ASSIGN_DIVIDEND_PORFIT_YOY")
    private Double assignDividendPorfitYoy;

    /**
     * 子公司支付给少数股东的股利、利润同比
     */
    @JsonProperty("SUBSIDIARY_PAY_DIVIDEND_YOY")
    private Double subsidiaryPayDividendYoy;

    /**
     * 购买子公司少数股权支付的现金同比
     */
    @JsonProperty("BUY_SUBSIDIARY_EQUITY_YOY")
    private Double buySubsidiaryEquityYoy;

    /**
     * 支付的其他与筹资活动有关的现金同比
     */
    @JsonProperty("PAY_OTHER_FINANCE_YOY")
    private Double payOtherFinanceYoy;

    /**
     * 子公司减少注册资本支付给少数股东的现金同比
     */
    @JsonProperty("SUBSIDIARY_REDUCE_CASH_YOY")
    private Double subsidiaryReduceCashYoy;

    /**
     * 筹资活动现金流出其他项目同比
     */
    @JsonProperty("FINANCE_OUTFLOW_OTHER_YOY")
    private Double financeOutflowOtherYoy;

    /**
     * 筹资活动现金流出小计同比
     */
    @JsonProperty("FINANCE_OUTFLOW_BALANCE_YOY")
    private Double financeOutflowBalanceYoy;

    /**
     * 筹资活动现金流出总额同比
     */
    @JsonProperty("TOTAL_FINANCE_OUTFLOW_YOY")
    private Double totalFinanceOutflowYoy;

    /**
     * 筹资活动产生的现金流量净额其他项目同比
     */
    @JsonProperty("FINANCE_NETCASH_OTHER_YOY")
    private Double financeNetcashOtherYoy;

    /**
     * 筹资活动产生的现金流量净额小计同比
     */
    @JsonProperty("FINANCE_NETCASH_BALANCE_YOY")
    private Double financeNetcashBalanceYoy;

    /**
     * 筹资活动产生的现金流量净额同比
     */
    @JsonProperty("NETCASH_FINANCE_YOY")
    private Double netcashFinanceYoy;

    /**
     * 汇率变动对现金及现金等价物的影响同比
     */
    @JsonProperty("RATE_CHANGE_EFFECT_YOY")
    private Double rateChangeEffectYoy;

    /**
     * 现金及现金等价物净增加额其他项目同比
     */
    @JsonProperty("CCE_ADD_OTHER_YOY")
    private Double cceAddOtherYoy;

    /**
     * 现金及现金等价物净增加额小计同比
     */
    @JsonProperty("CCE_ADD_BALANCE_YOY")
    private Double cceAddBalanceYoy;

    /**
     * 现金及现金等价物净增加额同比
     */
    @JsonProperty("CCE_ADD_YOY")
    private Double cceAddYoy;

    /**
     * 期初现金及现金等价物余额同比
     */
    @JsonProperty("BEGIN_CCE_YOY")
    private Double beginCceYoy;

    /**
     * 期末现金及现金等价物余额其他项目同比
     */
    @JsonProperty("END_CCE_OTHER_YOY")
    private Double endCceOtherYoy;

    /**
     * 期末现金及现金等价物余额小计同比
     */
    @JsonProperty("END_CCE_BALANCE_YOY")
    private Double endCceBalanceYoy;

    /**
     * 期末现金及现金等价物余额同比
     */
    @JsonProperty("END_CCE_YOY")
    private Double endCceYoy;

    /**
     * 净利润同比
     */
    @JsonProperty("NETPROFIT_YOY")
    private Double netProfitYoy;

    /**
     * 资产减值准备同比
     */
    @JsonProperty("ASSET_IMPAIRMENT_YOY")
    private Double assetImpairmentYoy;

    /**
     * 固定资产折旧、油气资产折耗、生产性生物资产折旧同比
     */
    @JsonProperty("FA_IR_DEPR_YOY")
    private Double faIrDeprYoy;

    /**
     * 油气资产折耗同比
     */
    @JsonProperty("OILGAS_BIOLOGY_DEPR_YOY")
    private Double oilgasBiologyDeprYoy;

    /**
     * 无形资产摊销同比
     */
    @JsonProperty("IR_DEPR_YOY")
    private Double irDeprYoy;

    /**
     * 长期待摊费用摊销同比
     */
    @JsonProperty("IA_AMORTIZE_YOY")
    private Double iaAmortizeYoy;

    /**
     * 递延收益摊销同比
     */
    @JsonProperty("LPE_AMORTIZE_YOY")
    private Double lpeAmortizeYoy;

    /**
     * 预付费用减少同比
     */
    @JsonProperty("PREPAID_EXPENSE_REDUCE_YOY")
    private Double prepaidExpenseReduceYoy;

    /**
     * 预提费用增加额同比
     */
    @JsonProperty("ACCRUED_EXPENSE_ADD_YOY")
    private Double accruedExpenseAddYoy;
    /**
     * 处置长期资产损失同比
     */
    @JsonProperty("DISPOSAL_LONGASSET_LOSS_YOY")
    private Double disposalLongassetLossYoy;
    /**
     * 固定资产报废损失同比
     */
    @JsonProperty("FA_SCRAP_LOSS_YOY")
    private Double faScrapLossYoy;
    /**
     * 公允价值变动损失同比
     */
    @JsonProperty("FAIRVALUE_CHANGE_LOSS_YOY")
    private Double fairvalueChangeLossYoy;
    /**
     * 财务费用同比
     */
    @JsonProperty("FINANCE_EXPENSE_YOY")
    private Double financeExpenseYoy;
    /**
     * 投资损失同比
     */
    @JsonProperty("INVEST_LOSS_YOY")
    private Double investLossYoy;
    /**
     * 递延所得税同比
     */
    @JsonProperty("DEFER_TAX_YOY")
    private Double deferTaxYoy;
    /**
     * 递延所得税资产减少额同比
     */
    @JsonProperty("DT_ASSET_REDUCE_YOY")
    private Double dtAssetReduceYoy;
    /**
     * 递延所得税负债增加额同比
     */
    @JsonProperty("DT_LIAB_ADD_YOY")
    private Double dtLiabAddYoy;
    /**
     * 预计负债增加额同比
     */
    @JsonProperty("PREDICT_LIAB_ADD_YOY")
    private Double predictLiabAddYoy;
    /**
     * 存货减少额同比
     */
    @JsonProperty("INVENTORY_REDUCE_YOY")
    private Double inventoryReduceYoy;
    /**
     * 经营性应收项目减少额同比
     */
    @JsonProperty("OPERATE_RECE_REDUCE_YOY")
    private Double operateReceReduceYoy;
    /**
     * 经营性应付项目增加额同比
     */
    @JsonProperty("OPERATE_PAYABLE_ADD_YOY")
    private Double operatePayableAddYoy;
    /**
     * 其他项目同比
     */
    @JsonProperty("OTHER_YOY")
    private Double otherYoy;
    /**
     * 经营活动产生的现金流量净额其他说明同比
     */
    @JsonProperty("OPERATE_NETCASH_OTHERNOTE_YOY")
    private String operateNetcashOthernoteYoy;
    /**
     * 经营活动产生的现金流量净额小计说明同比
     */
    @JsonProperty("OPERATE_NETCASH_BALANCENOTE_YOY")
    private String operateNetcashBalancenoteYoy;
    /**
     * 经营活动产生的现金流量净额说明同比
     */
    @JsonProperty("NETCASH_OPERATENOTE_YOY")
    private String netcashOperatenoteYoy;
    /**
     * 债务转为资本同比
     */
    @JsonProperty("DEBT_TRANSFER_CAPITAL_YOY")
    private Double debtTransferCapitalYoy;
    /**
     * 一年内到期的可转换公司债券同比
     */
    @JsonProperty("CONVERT_BOND_1YEAR_YOY")
    private Double convertBond1YearYoy;
    /**
     * 融资租入固定资产同比
     */
    @JsonProperty("FINLEASE_OBTAIN_FA_YOY")
    private Double finleaseObtainFaYoy;
    /**
     * 不涉及投资活动和筹资活动的其他项目同比
     */
    @JsonProperty("UNINVOLVE_INVESTFIN_OTHER_YOY")
    private Double uninvolveInvestfinOtherYoy;
    /**
     * 期末现金同比
     */
    @JsonProperty("END_CASH_YOY")
    private Double endCashYoy;
    /**
     * 期初现金同比
     */
    @JsonProperty("BEGIN_CASH_YOY")
    private Double beginCashYoy;
    /**
     * 期末现金等价物同比
     */
    @JsonProperty("END_CASH_EQUIVALENTS_YOY")
    private Double endCashEquivalentsYoy;
    /**
     * 期初现金等价物同比
     */
    @JsonProperty("BEGIN_CASH_EQUIVALENTS_YOY")
    private Double beginCashEquivalentsYoy;
    /**
     * 现金及现金等价物净增加额其他说明同比
     */
    @JsonProperty("CCE_ADD_OTHERNOTE_YOY")
    private String cceAddOthernoteYoy;
    /**
     * 现金及现金等价物净增加额小计说明同比
     */
    @JsonProperty("CCE_ADD_BALANCENOTE_YOY")
    private String cceAddBalancenoteYoy;
    /**
     * 现金及现金等价物净增加额说明同比
     */
    @JsonProperty("CCE_ADDNOTE_YOY")
    private String cceAddnoteYoy;
    /**
     * 审计意见类型
     */
    @JsonProperty("OPINION_TYPE")
    private String opinionType;
    /**
     * 其他审计意见类型
     */
    @JsonProperty("OSOPINION_TYPE")
    private String osopinionType;
    /**
     * 少数股东权益
     */
    @JsonProperty("MINORITY_INTEREST")
    private Double minorityInterest;
    /**
     * 少数股东权益同比
     */
    @JsonProperty("MINORITY_INTEREST_YOY")
    private Double minorityInterestYoy;
}
