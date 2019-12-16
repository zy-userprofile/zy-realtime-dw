package cn.zy.userprofile.realtime.dw.common.model

import cn.zy.userprofile.realtime.dw.common.utils.base.StringUtils
import cn.zy.userprofile.realtime.dw.common.utils.date.{DateTimeFormat, DateUtils}
import cn.zy.userprofile.realtime.dw.common.utils.fuctions._
import com.alibaba.fastjson.JSON
import com.google.common.base.Strings


/**
 *
 * @param eventid      事件ID
 * @param appversion   APP版本
 * @param siteid       站点ID
 * @param cookieid     cookieid
 * @param realmedium   真实媒介
 * @param realsource   真实来源
 * @param realcampaign 真实系列
 * @param medium       覆盖后媒介
 * @param source       覆盖后来源
 * @param campaign     覆盖后系列
 * @param isnewuser    是否新用户
 * @param deviceid     设备ID
 * @param devicename   设备名称
 * @param os           操作系统
 * @param os_version   操作系统版本
 * @param ipaddress    ip地址
 * @param ip_country   ip国家
 * @param ip_city      ip城市
 * @param eventkey     事件名称
 * @param eventcount
 * @param pagename     页面名称
 * @param userid       用户id
 * @param categoryid   类目id
 * @param referview
 * @param featurecode  专题跟踪码
 * @param goodsid      商品ID
 * @param goodsnum     商品数量
 * @param orderid      订单ID
 * @param pagenum
 * @param position     位置
 * @param keyword      搜索关键词
 * @param result       事件返回结果
 * @param algorithm    算法
 * @param area
 * @param revenue      收入
 * @param lable
 * @param clienttime   客户端时间
 * @param servertime   服务器时间
 * @param eventtime    事件时间
 * @param lang         语言
 * @param segment      segement字段:json格式存储
 * @param sid2         sid2
 * @param app_key      对应appsflyer的app_id
 * @param store_id     店铺id
 * @param data_date    数据日期
 */
case class CmEventLogMsg(
                            eventid: String
                            , appversion: String
                            , siteid: java.lang.Long
                            , cookieid: String
                            , realmedium: String
                            , realsource: String
                            , realcampaign: String
                            , medium: String
                            , source: String
                            , campaign: String
                            , isnewuser: java.lang.Integer
                            , deviceid: String
                            , devicename: String
                            , os: String
                            , os_version: String
                            , ipaddress: String
                            , ip_country: String
                            , ip_city: String
                            , eventkey: String
                            , eventcount: java.lang.Long
                            , pagename: String
                            , userid: java.lang.Long
                            , categoryid: java.lang.Long
                            , referview: String
                            , featurecode: String
                            , goodsid: java.lang.Long
                            , goodsnum: java.lang.Long
                            , orderid: java.lang.Long
                            , pagenum: java.lang.Long
                            , position: java.lang.Long
                            , keyword: String
                            , result: String
                            , algorithm: String
                            , area: String
                            , revenue: java.lang.Double
                            , lable: String
                            , clienttime: String
                            , servertime: String
                            , eventtime: String
                            , lang: String
                            , segment: String
                            , sid2: String
                            , app_key: String
                            , store_id: java.lang.Long
                            , data_date: String
                        ) {

    /**
     * 判断数据是否合法
     * 【作用】用于进行非法数据过滤
     *
     * @return
     */
    def isValid(): Boolean = {
        nvl[String](data_date, "").equals(DateUtils.parseDateStr(nvl[String](servertime, "0"), DateTimeFormat.TIME_SIGN_FORMAT.value(), DateTimeFormat.DATE_FORMAT.value()))
    }
}

object CmEventLogMsg {


    /**
     * 返回空对象
     *
     * @return
     */
    def empty(): CmEventLogMsg = {

        new CmEventLogMsg(
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null,
            null, null, null, null, null
        )
    }


    /**
     * Json数据解析
     *
     * @param msg  Json字符串
     * @return     CmPageViewLogMsg对象
     */
    def parse(msg: String): CmEventLogMsg = {

        if (Strings.isNullOrEmpty(msg)) {
            return CmEventLogMsg.empty()
        }

        val data = JSON.parseObject(msg)
        val c = data.getJSONObject("c")
        val d = data.getJSONObject("d")
        val i = data.getJSONObject("i")
        val l = data.getJSONObject("l")
        val segmentation = i.getJSONObject("segmentation")


        val eventid: String = data.getString("_id")
        val appversion: String = data.getString("v")
        val siteid: java.lang.Long = StringUtils.parseLong(c.getString("tid"))
        val cookieid: String = c.getString("kid")
        val realmedium: String = c.getString("rmdi")
        val realsource: String = c.getString("rsrc")
        val realcampaign: String = c.getString("rcam")
        val medium: String = c.getString("mdi")
        val source: String = c.getString("src")
        val campaign: String = c.getString("cam")
        val isnewuser: java.lang.Integer = StringUtils.parseInt(c.getString("is_new"))
        val deviceid: String = d.getString("id")
        val devicename: String = d.getString("d")
        val os: String = if (d.getString("p") == null) segmentation.getString("segment") else d.getString("p")
        val os_version: String = d.getString("pv")
        val ipaddress: String = l.getString("ip")
        val ip_country: String = l.getString("cc")
        val ip_city: String = l.getString("cty")
        val eventkey: String = i.getString("key")
        val eventcount: java.lang.Long = StringUtils.parseLong(i.getString("count"))
        val pagename: String = if (segmentation == null) null else segmentation.getString("pag")
        val userid: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("uid"))
        val categoryid: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("cid"))
        val referview: String = if (segmentation == null) null else segmentation.getString("ref")
        val featurecode: String = if (segmentation == null) null else segmentation.getString("lcm")
        val goodsid: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("gid"))
        val goodsnum: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("num"))
        val orderid: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("oid"))
        val pagenum: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("pnm"))
        val position: java.lang.Long = if (segmentation == null) null else StringUtils.parseLong(segmentation.getString("pos"))
        val keyword: String = if (segmentation == null) null else segmentation.getString("key")
        val result: String = if (segmentation == null) null else segmentation.getString("res")
        val algorithm: String = if (segmentation == null) null else segmentation.getString("alt")
        val revenue: java.lang.Double = if (segmentation == null) null else StringUtils.parseDouble(segmentation.getString("rev"))
        val area: String = if (segmentation == null) null else segmentation.getString("are")
        val lable: String = if (segmentation == null) null else segmentation.getString("lbl")
        val clienttime: String = DateUtils.fromUnixtime(data.getString("ts").toLong * 1000L, DateTimeFormat.TIME_SIGN_FORMAT.value())
        val servertime: String = DateUtils.fromUnixtime(BigDecimal(data.getString("reqts")).asInstanceOf[Number].longValue() * 1000L, DateTimeFormat.TIME_SIGN_FORMAT.value())
        val eventtime: String = DateUtils.fromUnixtime(i.getString("timestamp").toLong * 1000L, DateTimeFormat.TIME_SIGN_FORMAT.value())
        val lang: String = c.getString("lang")
        val segment: String = i.getString("segmentation")
        val sid2: String = c.getString("sid2")
        val app_key: String = if (data.getString("k") == null) "" else data.getString("k")
        val store_id: java.lang.Long = if (segmentation.getString("stid") == null) -1L else StringUtils.parseLong(segmentation.getString("stid"))
        val data_date: String = nvl[String](DateUtils.parseDateStr(servertime, DateTimeFormat.TIME_SIGN_FORMAT.value(), DateTimeFormat.DATE_FORMAT.value()), DateUtils.nowDate())

        CmEventLogMsg(
            eventid
            , appversion
            , siteid
            , cookieid
            , realmedium
            , realsource
            , realcampaign
            , medium
            , source
            , campaign
            , isnewuser
            , deviceid
            , devicename
            , os
            , os_version
            , ipaddress
            , ip_country
            , ip_city
            , eventkey
            , eventcount
            , pagename
            , userid
            , categoryid
            , referview
            , featurecode
            , goodsid
            , goodsnum
            , orderid
            , pagenum
            , position
            , keyword
            , result
            , algorithm
            , area
            , revenue
            , lable
            , clienttime
            , servertime
            , eventtime
            , lang
            , segment
            , sid2
            , app_key
            , store_id
            , data_date
        )
    }

}
