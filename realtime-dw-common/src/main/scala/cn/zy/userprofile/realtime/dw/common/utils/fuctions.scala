package cn.zy.userprofile.realtime.dw.common.utils

object fuctions {

    def nvl[T](v1: T, v2: T): T = {
        if (v1 == null) v2 else v1
    }

    def coalesce[T](v1: T, v2: T, v3: T): T = {
        if (v1 == null && v2 == null) v3
        else if (v1 == null) v2
        else v1
    }

}
