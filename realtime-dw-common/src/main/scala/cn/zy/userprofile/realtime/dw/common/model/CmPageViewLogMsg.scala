package cn.zy.userprofile.realtime.dw.common.model

import cn.zy.userprofile.realtime.dw.common.utils.base.StringUtils
import cn.zy.userprofile.realtime.dw.common.utils.date.{DateTimeFormat, DateUtils}
import com.google.common.base.Strings
import cn.zy.userprofile.realtime.dw.common.utils.fuctions._
import com.alibaba.fastjson.JSON

/**
 * author: hufenggang
 * email: hufenggang2019@gmail.com
 * date: 2019/12/24 15:55
 */
case class CmPageViewLogMsg(
                               pageviewid: String,
                               appversion: String,
                               siteid: String,
                               cookieid: String,
                               realmedium: String,
                               realsource: String,
                               realcampaign: String,
                               medium: String,
                               source: String,
                               campaign: String,
                               isnewuser: String,
                               deviceid: String,
                               devicename: String,
                               os: String,
                               os_version: String,
                               ipaddress: String,
                               ip_country: String,
                               ip_city: String,
                               pagename: String,
                               visitcount: String,
                               begin_pageview: String,
                               pageview_duration: String,
                               var clienttime: String,
                               var servertime: String,
                               var eventtime: String,
                               lang: String,
                               segment: String,
                               sid2: String,
                               app_key: String,
                               data_date: String
                           ) {

    /**
     * 判断数据是否合法
     * 【作用】用于进行非法数据过滤
     *
     * @return
     */
    def isValid(): Boolean = {
        val regex = "[0-9a-zA-Z-]*".r

        !Strings.isNullOrEmpty(cookieid) &&
            nvl[String](cookieid, "").equals(regex.findPrefixOf(cookieid).get) &&
            nvl[String](data_date, "").equals(DateUtils.parseDateStr(eventtime, DateTimeFormat.TIME_SIGN_FORMAT.value(), DateTimeFormat.DATE_FORMAT.value()))
    }
}

object CmPageViewLogMsg {

    /**
     * 返回空对象
     *
     * @return
     */
    def empty(): CmPageViewLogMsg = {
        new CmPageViewLogMsg(
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
     * @param msg Json字符串
     * @return CmPageViewLogMsg对象
     */
    def parse(msg: String): CmPageViewLogMsg = {

        val regex = "[0-9a-zA-Z-]*".r

        if (Strings.isNullOrEmpty(msg)) {
            return CmPageViewLogMsg.empty()
        }

        val data = JSON.parseObject(msg)
        val c = data.getJSONObject("c")
        val d = data.getJSONObject("d")
        val i = data.getJSONObject("i")
        val l = data.getJSONObject("l")
        val segmentation = i.getJSONObject("segmentation")


        val pageviewid: String = data.getString("_id")
        val appversion: String = data.getString("v")
        val siteid: String = c.getString("tid")
        val cookieid: String = c.getString("kid")
        val realmedium: String = c.getString("rmdi")
        val realsource: String = c.getString("rsrc")
        val realcampaign: String = c.getString("rcam")
        val medium: String = c.getString("mdi")
        val source: String = c.getString("src")
        val campaign: String = c.getString("cam")
        val isnewuser: String = c.getString("is_new")
        val deviceid: String = if (d.getString("id") == null || "".equals(d.getString("id"))) "" else {
            if (regex.findPrefixOf(d.getString("id")).get.equals(d.getString("id"))) d.getString("id") else ""
        }
        val devicename: String = d.getString("d")
        val os: String = if ("".equals(d.getString("p"))) segmentation.getString("segment") else d.getString("p")
        val os_version = d.getString("pv")
        val ipaddress: String = l.getString("ip")
        val ip_country: String = l.getString("cc")
        val ip_city: String = l.getString("cty")
        val pagename: String = segmentation.getString("name")
        val visitcount: String = if (segmentation.getString("visit") == null) "0" else segmentation.getString("visit")
        val begin_pageview: String = segmentation.getString("start")
        val pageview_duration: String = if (os == null || os.isEmpty) {
            "0"
        }
        else {
            if (os.toString.equalsIgnoreCase("ios")) {
                if (i.getString("dur") == null || i.getString("dur").isEmpty) "0" else i.getString("dur")
            } else if (os.toString.equalsIgnoreCase("android")) {
                if (segmentation.getString("dur") == null || segmentation.getString("dur").isEmpty) "0" else segmentation.getString("dur")
            } else "0"
        }
        val clienttime: String = DateUtils.fromUnixtime(StringUtils.parseLong(data.getString("ts")) * 1000L, DateTimeFormat.TIME_SIGN_FORMAT.value())
        val servertime: String = DateUtils.fromUnixtime(BigDecimal(StringUtils.parseDouble(data.getString("reqts"))).asInstanceOf[Number].longValue() * 1000L, DateTimeFormat.TIME_SIGN_FORMAT.value())
        val eventtime: String = DateUtils.fromUnixtime(StringUtils.parseLong(i.getString("timestamp")) * 1000L, DateTimeFormat.TIME_SIGN_FORMAT.value())
        val lang: String = c.getString("lang")
        val segment: String = i.getString("segmentation")
        val sid2: String = c.getString("sid2")
        val app_key: String = if (data.getString("k") == null) "" else data.getString("k")
        val data_date: String = nvl[String](DateUtils.parseDateStr(servertime, DateTimeFormat.TIME_SIGN_FORMAT.value(), DateTimeFormat.DATE_FORMAT.value()), DateUtils.nowDate())


        CmPageViewLogMsg(
            pageviewid
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
            , pagename
            , visitcount
            , begin_pageview
            , pageview_duration
            , clienttime
            , servertime
            , eventtime
            , lang
            , segment
            , sid2
            , app_key
            , data_date
        )

    }
}
