/**
  * Created by ilkkzm on 17-4-28.
  */
object ByteTest extends App{
  val str = "交易时间,商户号,商户APPID,子商户号,子商户APPID,用户标识,设备号,支付场景,商户订单号,QQ钱包订单号,付款银行,货币种类,订单金额(元),商户优惠金额(元),商户应收金额(元),QQ钱包优惠金额(元),用户支付金额(元),交易状态,退款提交时间,商户退款订单号,QQ钱包退款订单号,退款金额(元),退还QQ钱包优惠金额(元),退款状态,退款成功时间,退款方式,商品名称,商户数据包,手续费金额(元),费率"
  println(str.getBytes("UTF-8").length)
}
