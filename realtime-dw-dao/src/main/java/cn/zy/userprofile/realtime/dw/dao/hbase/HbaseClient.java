package cn.zy.userprofile.realtime.dw.dao.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class HbaseClient {
    private static Logger logger = LoggerFactory.getLogger(HbaseClient.class);

    /**
     * 获取HBase连接
     *
     * @param configMap HBase配置文件
     * @return HBase连接
     * @throws IOException
     */
    public static Connection getConnection(Map<String, String> configMap) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Iterator<Map.Entry<String, String>> iterator = configMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            conf.set(next.getKey(), next.getValue());
        }
        return ConnectionFactory.createConnection(conf);
    }


    /**
     * 创建HBase表
     *
     * @param conn           HBase连接
     * @param tableName      HBase表名
     * @param columnFamilies HBase列族名
     * @throws IOException
     */
    public static void createTable(Connection conn, String tableName, String... columnFamilies) throws IOException {
        TableName tablename = TableName.valueOf(tableName);
        Admin admin = conn.getAdmin();

        if (admin.tableExists(tablename)) {
            logger.warn("HBase table is exists!");
        } else {
            System.out.println("Start create table");
            HTableDescriptor tableDescriptor = new HTableDescriptor(tablename);
            for (String columnFamliy : columnFamilies) {
                HTableDescriptor column = tableDescriptor.addFamily(new HColumnDescriptor(columnFamliy));
            }
            admin.createTable(tableDescriptor);
            logger.info("Create Table success");
        }
    }


    /**
     * 获取一行数据
     *
     * @param conn       HBase连接
     * @param tableName  HBase表名
     * @param rowKey     行键
     * @param famliyName 列族名
     * @param column     列名
     * @return
     * @throws IOException
     */
    public static String get(Connection conn, String tableName, String rowKey, String famliyName, String column) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        byte[] row = Bytes.toBytes(rowKey);
        Get get = new Get(row);
        Result result = table.get(get);
        byte[] resultValue = result.getValue(famliyName.getBytes(), column.getBytes());
        if (null == resultValue) {
            return null;
        }
        return new String(resultValue);
    }


    /**
     * 获取一行的所有数据 并且排序
     *
     * @param conn
     * @param tableName
     * @param rowKey
     * @return
     * @throws IOException
     */
    public static List<Map.Entry> getRow(Connection conn, String tableName, String rowKey) throws IOException {
        Table table = conn.getTable(TableName.valueOf(tableName));
        byte[] row = Bytes.toBytes(rowKey);
        Get get = new Get(row);
        Result r = table.get(get);

        HashMap<String, Double> rst = new HashMap<>();

        for (Cell cell : r.listCells()) {
            String key = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            rst.put(key, new Double(value));
        }

        List<Map.Entry> ans = new ArrayList<>();
        ans.addAll(rst.entrySet());

        Collections.sort(ans, (m1, m2) -> new Double((Double) m1.getValue() - (Double) m2.getValue()).intValue());

        return ans;
    }


    /**
     * 向对应列添加数据
     *
     * @param conn       HBase连接
     * @param tableName  HBase表名
     * @param rowKey     行键
     * @param famliyName 列族名
     * @param column     列名
     * @param value      值
     * @throws Exception
     */
    public static void put(Connection conn, String tableName, String rowKey, String famliyName, String column, String value) throws Exception {
        Table table = conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(famliyName.getBytes(), column.getBytes(), value.getBytes());
        table.put(put);
    }


    /**
     * 列值加1
     *
     * @param conn       HBase连接
     * @param tableName  HBase表名
     * @param rowKey     行键
     * @param famliyName 列族名
     * @param column     列名
     * @throws Exception
     */
    public static void increamColumn(Connection conn, String tableName, String rowKey, String famliyName, String column) throws Exception {
        String val = get(conn, tableName, rowKey, famliyName, column);
        int res = 1;
        if (val != null) {
            res = Integer.valueOf(val) + 1;
        }
        put(conn, tableName, rowKey, famliyName, column, String.valueOf(res));
    }

    /**
     * 取出表中所有的key
     *
     * @param conn      HBase连接
     * @param tableName HBase表名
     * @return
     * @throws IOException
     */
    public static List<String> getAllKey(Connection conn, String tableName) throws IOException {
        List<String> keys = new ArrayList<>();
        Scan scan = new Scan();
        Table table = conn.getTable(TableName.valueOf(tableName));
        ResultScanner scanner = table.getScanner(scan);
        for (Result r : scanner) {
            keys.add(new String(r.getRow()));
        }
        return keys;
    }

}
