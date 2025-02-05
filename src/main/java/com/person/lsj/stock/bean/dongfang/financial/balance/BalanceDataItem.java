package com.person.lsj.stock.bean.dongfang.financial.balance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BalanceDataItem {

    // ------------------ 基础信息 ------------------
    @JsonProperty("SECUCODE")
    private String secucode; // 证券代码

    @JsonProperty("SECURITY_CODE")
    private String securityCode; // 证券代码（简码）

    @JsonProperty("SECURITY_NAME_ABBR")
    private String securityNameAbbr; // 证券简称

    @JsonProperty("ORG_CODE")
    private String orgCode; // 组织机构代码

    @JsonProperty("ORG_TYPE")
    private String orgType; // 机构类型

    // ------------------ 报告信息 ------------------
    @JsonProperty("REPORT_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportDate; // 报告日期

    @JsonProperty("REPORT_TYPE")
    private String reportType; // 报告类型（如年报、季报）

    @JsonProperty("REPORT_DATE_NAME")
    private String reportDateName; // 报告期名称（如2023年报）

    @JsonProperty("SECURITY_TYPE_CODE")
    private String securityTypeCode; // 证券类型代码

    // ------------------ 关键日期 ------------------
    @JsonProperty("NOTICE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime noticeDate; // 公告日期

    @JsonProperty("UPDATE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate; // 更新日期

    // ------------------ 财务指标 ------------------
    @JsonProperty("CURRENCY")
    private String currency; // 货币单位

    @JsonProperty("ACCEPT_DEPOSIT_INTERBANK")
    private Double acceptDepositInterbank; // 同业存放款项

    @JsonProperty("ACCOUNTS_PAYABLE")
    private Double accountsPayable; // 应付账款

    @JsonProperty("ACCOUNTS_RECE")
    private Double accountsRece; // 应收账款

    @JsonProperty("ACCRUED_EXPENSE")
    private Double accruedExpense; // 应计费用

    @JsonProperty("ADVANCE_RECEIVABLES")
    private Double advanceReceivables; // 预收款项

    @JsonProperty("AGENT_TRADE_SECURITY")
    private Double agentTradeSecurity; // 代理买卖证券款

    @JsonProperty("AGENT_UNDERWRITE_SECURITY")
    private Double agentUnderwriteSecurity; // 代理承销证券款

    @JsonProperty("AMORTIZE_COST_FINASSET")
    private Double amortizeCostFinasset; // 以摊余成本计量的金融资产

    @JsonProperty("AMORTIZE_COST_FINLIAB")
    private Double amortizeCostFinliab; // 以摊余成本计量的金融负债

    @JsonProperty("AMORTIZE_COST_NCFINASSET")
    private Double amortizeCostNcfinasset; // 以摊余成本计量的非金融资产

    @JsonProperty("AMORTIZE_COST_NCFINLIAB")
    private Double amortizeCostNcfinliab; // 以摊余成本计量的非金融负债

    @JsonProperty("APPOINT_FVTPL_FINASSET")
    private Double appointFvtplFinasset; // 指定为以公允价值计量且其变动计入当期损益的金融资产

    @JsonProperty("APPOINT_FVTPL_FINLIAB")
    private Double appointFvtplFinliab; // 指定为以公允价值计量且其变动计入当期损益的金融负债

    @JsonProperty("ASSET_BALANCE")
    private Double assetBalance; // 资产总计

    @JsonProperty("ASSET_OTHER")
    private Double assetOther; // 其他资产

    @JsonProperty("ASSIGN_CASH_DIVIDEND")
    private Double assignCashDividend; // 分配现金股利

    @JsonProperty("AVAILABLE_SALE_FINASSET")
    private Double availableSaleFinasset; // 可供出售金融资产

    @JsonProperty("BOND_PAYABLE")
    private Double bondPayable; // 应付债券

    @JsonProperty("BORROW_FUND")
    private Double borrowFund; // 借入资金

    @JsonProperty("BUY_RESALE_FINASSET")
    private Double buyResaleFinasset; // 买入返售金融资产

    @JsonProperty("CAPITAL_RESERVE")
    private Double capitalReserve; // 资本公积

    @JsonProperty("CIP")
    private Double cip; // 在建工程

    @JsonProperty("CONSUMPTIVE_BIOLOGICAL_ASSET")
    private Double consumptiveBiologicalAsset; // 消耗性生物资产

    @JsonProperty("CONTRACT_ASSET")
    private Double contractAsset; // 合同资产

    @JsonProperty("CONTRACT_LIAB")
    private Double contractLiab; // 合同负债

    @JsonProperty("CONVERT_DIFF")
    private Double convertDiff; // 可转换债券差额

    @JsonProperty("CREDITOR_INVEST")
    private Double creditorInvest; // 债权人投资

    @JsonProperty("CURRENT_ASSET_BALANCE")
    private Double currentAssetBalance; // 流动资产合计

    @JsonProperty("CURRENT_ASSET_OTHER")
    private Double currentAssetOther; // 其他流动资产

    @JsonProperty("CURRENT_LIAB_BALANCE")
    private Double currentLiabBalance; // 流动负债合计

    @JsonProperty("CURRENT_LIAB_OTHER")
    private Double currentLiabOther; // 其他流动负债

    @JsonProperty("DEFER_INCOME")
    private Double deferIncome; // 递延收益

    @JsonProperty("DEFER_INCOME_1YEAR")
    private Double deferIncome1year; // 一年内到期的递延收益

    @JsonProperty("DEFER_TAX_ASSET")
    private Double deferTaxAsset; // 递延所得税资产

    @JsonProperty("DEFER_TAX_LIAB")
    private Double deferTaxLiab; // 递延所得税负债

    @JsonProperty("DERIVE_FINASSET")
    private Double deriveFinasset; // 衍生金融资产

    @JsonProperty("DERIVE_FINLIAB")
    private Double deriveFinliab; // 衍生金融负债

    @JsonProperty("DEVELOP_EXPENSE")
    private Double developExpense; // 开发支出

    @JsonProperty("DIV_HOLDSALE_ASSET")
    private Double divHoldsaleAsset; // 划分为持有待售的资产

    @JsonProperty("DIV_HOLDSALE_LIAB")
    private Double divHoldsaleLiab; // 划分为持有待售的负债

    @JsonProperty("DIVIDEND_PAYABLE")
    private Double dividendPayable; // 应付股利

    @JsonProperty("DIVIDEND_RECE")
    private Double dividendRece; // 应收股利

    @JsonProperty("EQUITY_BALANCE")
    private Double equityBalance; // 股东权益合计

    @JsonProperty("EQUITY_OTHER")
    private Double equityOther; // 其他权益

    @JsonProperty("EXPORT_REFUND_RECE")
    private Double exportRefundRece; // 出口退税款

    @JsonProperty("FEE_COMMISSION_PAYABLE")
    private Double feeCommissionPayable; // 应付手续费及佣金

    @JsonProperty("FIN_FUND")
    private Double finFund; // 金融资金

    @JsonProperty("FINANCE_RECE")
    private Double financeRece; // 应收融资款

    @JsonProperty("FIXED_ASSET")
    private Double fixedAsset; // 固定资产

    @JsonProperty("FIXED_ASSET_DISPOSAL")
    private Double fixedAssetDisposal; // 固定资产清理

    @JsonProperty("FVTOCI_FINASSET")
    private Double fvtociFinasset; // 以公允价值计量且其变动计入其他综合收益的金融资产

    @JsonProperty("FVTOCI_NCFINASSET")
    private Double fvtociNcfinasset; // 以公允价值计量且其变动计入其他综合收益的非金融资产

    @JsonProperty("FVTPL_FINASSET")
    private Double fvtplFinasset; // 以公允价值计量且其变动计入当期损益的金融资产

    @JsonProperty("FVTPL_FINLIAB")
    private Double fvtplFinliab; // 以公允价值计量且其变动计入当期损益的金融负债

    @JsonProperty("GENERAL_RISK_RESERVE")
    private Double generalRiskReserve; // 一般风险准备

    @JsonProperty("GOODWILL")
    private Double goodwill; // 商誉

    @JsonProperty("HOLD_MATURITY_INVEST")
    private Double holdMaturityInvest; // 持有至到期投资

    @JsonProperty("HOLDSALE_ASSET")
    private Double holdsaleAsset; // 持有待售资产

    @JsonProperty("HOLDSALE_LIAB")
    private Double holdsaleLiab; // 持有待售负债

    @JsonProperty("INSURANCE_CONTRACT_RESERVE")
    private Double insuranceContractReserve; // 保险合同准备金

    @JsonProperty("INTANGIBLE_ASSET")
    private Double intangibleAsset; // 无形资产

    @JsonProperty("INTEREST_PAYABLE")
    private Double interestPayable; // 应付利息

    @JsonProperty("INTEREST_RECE")
    private Double interestRece; // 应收利息

    @JsonProperty("INTERNAL_PAYABLE")
    private Double internalPayable; // 内部应付款

    @JsonProperty("INTERNAL_RECE")
    private Double internalRece; // 内部应收款

    @JsonProperty("INVENTORY")
    private Double inventory; // 存货

    @JsonProperty("INVEST_REALESTATE")
    private Double investRealestate; // 投资性房地产

    @JsonProperty("LEASE_LIAB")
    private Double leaseLiab; // 租赁负债

    @JsonProperty("LEND_FUND")
    private Double lendFund; // 拆出资金

    @JsonProperty("LIAB_BALANCE")
    private Double liabBalance; // 负债合计

    @JsonProperty("LIAB_EQUITY_BALANCE")
    private Double liabEquityBalance; // 负债和股东权益总计

    @JsonProperty("LIAB_EQUITY_OTHER")
    private Double liabEquityOther; // 其他负债和股东权益

    @JsonProperty("LIAB_OTHER")
    private Double liabOther; // 其他负债

    @JsonProperty("LOAN_ADVANCE")
    private Double loanAdvance; // 贷款及垫款

    @JsonProperty("LOAN_PBC")
    private Double loanPbc; // 向中央银行借款

    @JsonProperty("LONG_EQUITY_INVEST")
    private Double longEquityInvest; // 长期股权投资

    @JsonProperty("LONG_LOAN")
    private Double longLoan; // 长期借款

    @JsonProperty("LONG_PAYABLE")
    private Double longPayable; // 长期应付款

    @JsonProperty("LONG_PREPAID_EXPENSE")
    private Double longPrepaidExpense; // 长期待摊费用

    @JsonProperty("LONG_RECE")
    private Double longRece; // 长期应收款

    @JsonProperty("LONG_STAFFSALARY_PAYABLE")
    private Double longStaffsalaryPayable; // 长期应付职工薪酬

    @JsonProperty("MINORITY_EQUITY")
    private Double minorityEquity; // 少数股东权益

    @JsonProperty("MONETARYFUNDS")
    private Double monetaryFunds; // 货币资金

    @JsonProperty("NONCURRENT_ASSET_1YEAR")
    private Double noncurrentAsset1year; // 一年内到期的非流动资产

    @JsonProperty("NONCURRENT_ASSET_BALANCE")
    private Double noncurrentAssetBalance; // 非流动资产合计

    @JsonProperty("NONCURRENT_ASSET_OTHER")
    private Double noncurrentAssetOther; // 其他非流动资产

    @JsonProperty("NONCURRENT_LIAB_1YEAR")
    private Double noncurrentLiab1year; // 一年内到期的非流动负债

    @JsonProperty("NONCURRENT_LIAB_BALANCE")
    private Double noncurrentLiabBalance; // 非流动负债合计

    @JsonProperty("NONCURRENT_LIAB_OTHER")
    private Double noncurrentLiabOther; // 其他非流动负债

    @JsonProperty("NOTE_ACCOUNTS_PAYABLE")
    private Double noteAccountsPayable; // 应付票据

    @JsonProperty("NOTE_ACCOUNTS_RECE")
    private Double noteAccountsRece; // 应收票据

    @JsonProperty("NOTE_PAYABLE")
    private Double notePayable; // 应付票据

    @JsonProperty("NOTE_RECE")
    private Double noteRece; // 应收票据

    @JsonProperty("OIL_GAS_ASSET")
    private Double oilGasAsset; // 油气资产

    @JsonProperty("OTHER_COMPRE_INCOME")
    private Double otherCompreIncome; // 其他综合收益

    @JsonProperty("OTHER_CREDITOR_INVEST")
    private Double otherCreditorInvest; // 其他债权人投资

    @JsonProperty("OTHER_CURRENT_ASSET")
    private Double otherCurrentAsset; // 其他流动资产

    @JsonProperty("OTHER_CURRENT_LIAB")
    private Double otherCurrentLiab; // 其他流动负债

    @JsonProperty("OTHER_EQUITY_INVEST")
    private Double otherEquityInvest; // 其他权益投资

    @JsonProperty("OTHER_EQUITY_OTHER")
    private Double otherEquityOther; // 其他权益

    @JsonProperty("OTHER_EQUITY_TOOL")
    private Double otherEquityTool; // 其他权益工具

    @JsonProperty("OTHER_NONCURRENT_ASSET")
    private Double otherNoncurrentAsset; // 其他非流动资产

    @JsonProperty("OTHER_NONCURRENT_FINASSET")
    private Double otherNoncurrentFinasset; // 其他非金融资产

    @JsonProperty("OTHER_NONCURRENT_LIAB")
    private Double otherNoncurrentLiab; // 其他非流动负债

    @JsonProperty("OTHER_PAYABLE")
    private Double otherPayable; // 其他应付款

    @JsonProperty("OTHER_RECE")
    private Double otherRece; // 其他应收款

    @JsonProperty("PARENT_EQUITY_BALANCE")
    private Double parentEquityBalance; // 母公司股东权益合计

    @JsonProperty("PARENT_EQUITY_OTHER")
    private Double parentEquityOther; // 母公司其他权益

    @JsonProperty("PERPETUAL_BOND")
    private Double perpetualBond; // 永续债

    @JsonProperty("PERPETUAL_BOND_PAYBALE")
    private Double perpetualBondPaybale; // 应付永续债

    @JsonProperty("PREDICT_CURRENT_LIAB")
    private Double predictCurrentLiab; // 预计流动负债

    @JsonProperty("PREDICT_LIAB")
    private Double predictLiab; // 预计负债

    @JsonProperty("PREFERRED_SHARES")
    private Double preferredShares; // 优先股

    @JsonProperty("PREFERRED_SHARES_PAYBALE")
    private Double preferredSharesPaybale; // 应付优先股

    @JsonProperty("PREMIUM_RECE")
    private Double premiumRece; // 应收保费

    @JsonProperty("PREPAYMENT")
    private Double prepayment; // 预付款项

    @JsonProperty("PRODUCTIVE_BIOLOGY_ASSET")
    private Double productiveBiologyAsset; // 生产性生物资产

    @JsonProperty("PROJECT_MATERIAL")
    private Double projectMaterial; // 工程物资

    @JsonProperty("RC_RESERVE_RECE")
    private Double rcReserveRece; // 应收再保险准备金

    @JsonProperty("REINSURE_PAYABLE")
    private Double reinsurePayable; // 应付再保险款项

    @JsonProperty("REINSURE_RECE")
    private Double reinsureRece; // 应收再保险款项

    @JsonProperty("SELL_REPO_FINASSET")
    private Double sellRepoFinasset; // 卖出回购金融资产款

    @JsonProperty("SETTLE_EXCESS_RESERVE")
    private Double settleExcessReserve; // 结算备付金

    @JsonProperty("SHARE_CAPITAL")
    private Double shareCapital; // 股本

    @JsonProperty("SHORT_BOND_PAYABLE")
    private Double shortBondPayable; // 短期应付债券

    @JsonProperty("SHORT_FIN_PAYABLE")
    private Double shortFinPayable; // 短期应付金融款

    @JsonProperty("SHORT_LOAN")
    private Double shortLoan; // 短期借款

    @JsonProperty("SPECIAL_PAYABLE")
    private Double specialPayable; // 专项应付款

    @JsonProperty("SPECIAL_RESERVE")
    private Double specialReserve; // 专项储备

    @JsonProperty("STAFF_SALARY_PAYABLE")
    private Double staffSalaryPayable; // 应付职工薪酬

    @JsonProperty("SUBSIDY_RECE")
    private Double subsidyRece; // 应收补贴款

    @JsonProperty("SURPLUS_RESERVE")
    private Double surplusReserve; // 盈余公积

    @JsonProperty("TAX_PAYABLE")
    private Double taxPayable; // 应交税费

    @JsonProperty("TOTAL_ASSETS")
    private Double totalAssets; // 资产总计

    @JsonProperty("TOTAL_CURRENT_ASSETS")
    private Double totalCurrentAssets; // 流动资产合计

    @JsonProperty("TOTAL_CURRENT_LIAB")
    private Double totalCurrentLiab; // 流动负债合计

    @JsonProperty("TOTAL_EQUITY")
    private Double totalEquity; // 股东权益合计

    @JsonProperty("TOTAL_LIAB_EQUITY")
    private Double totalLiabEquity; // 负债和股东权益总计

    @JsonProperty("TOTAL_LIABILITIES")
    private Double totalLiabilities; // 负债合计

    @JsonProperty("TOTAL_NONCURRENT_ASSETS")
    private Double totalNoncurrentAssets; // 非流动资产合计

    @JsonProperty("TOTAL_NONCURRENT_LIAB")
    private Double totalNoncurrentLiab; // 非流动负债合计

    @JsonProperty("TOTAL_OTHER_PAYABLE")
    private Double totalOtherPayable; // 其他应付款合计

    @JsonProperty("TOTAL_OTHER_RECE")
    private Double totalOtherRece; // 其他应收款合计

    @JsonProperty("TOTAL_PARENT_EQUITY")
    private Double totalParentEquity; // 母公司

    @JsonProperty("TRADE_FINASSET")
    private Double tradeFinasset;

    @JsonProperty("TRADE_FINASSET_NOTFVTPL")
    private Double tradeFinassetNotfvtpl;

    @JsonProperty("TRADE_FINLIAB")
    private Double tradeFinliab;

    @JsonProperty("TRADE_FINLIAB_NOTFVTPL")
    private Double tradeFinliabNotfvtpl;

    @JsonProperty("TREASURY_SHARES")
    private Double treasuryShares;

    @JsonProperty("UNASSIGN_RPOFIT")
    private Double unassignRpofit;

    @JsonProperty("UNCONFIRM_INVEST_LOSS")
    private Double unconfirmInvestLoss;

    @JsonProperty("USERIGHT_ASSET")
    private Double userightAsset;

    // ------------------ YoY 增长字段 ------------------
    @JsonProperty("ACCEPT_DEPOSIT_INTERBANK_YOY")
    private Double acceptDepositInterbankYoy;

    @JsonProperty("ACCOUNTS_PAYABLE_YOY")
    private Double accountsPayableYoy;

    @JsonProperty("ACCOUNTS_RECE_YOY")
    private Double accountsReceYoy;

    // 补充所有 YoY 字段...
    @JsonProperty("ACCRUED_EXPENSE_YOY")
    private Double accruedExpenseYoy;

    @JsonProperty("ADVANCE_RECEIVABLES_YOY")
    private Double advanceReceivablesYoy;

    @JsonProperty("AGENT_TRADE_SECURITY_YOY")
    private Double agentTradeSecurityYoy;

    @JsonProperty("AGENT_UNDERWRITE_SECURITY_YOY")
    private Double agentUnderwriteSecurityYoy;

    @JsonProperty("AMORTIZE_COST_FINASSET_YOY")
    private Double amortizeCostFinassetYoy;

    @JsonProperty("AMORTIZE_COST_FINLIAB_YOY")
    private Double amortizeCostFinliabYoy;

    // ...（剩余 YoY 字段按相同模式补充）

    // ------------------ 其他字段 ------------------
    @JsonProperty("OPINION_TYPE")
    private String opinionType;

    @JsonProperty("OSOPINION_TYPE")
    private String osOpinionType;

    @JsonProperty("LISTING_STATE")
    private String listingState;
}
