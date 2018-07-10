/**
 * Created by User on 7/6/2018.
 */
public class Queries {
    public static final String sql1 = "select l_returnflag, l_linestatus, sum(l_quantity) as sum_qty, sum(l_extendedprice) as sum_base_price, sum(l_extendedprice * (1 - l_discount)) as sum_disc_price, sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)) as sum_charge, avg(l_quantity) as avg_qty, avg(l_extendedprice) as avg_price, avg(l_discount) as avg_disc, count(*) as count_order from lineitem where l_shipdate <= date '1998-12-01' - ?::INTERVAL group by l_returnflag, l_linestatus order by l_returnflag, l_linestatus LIMIT 1;";
    public static final String sql2 = "select s_acctbal, s_name, n_name, p_partkey, p_mfgr, s_address, s_phone, s_comment from part, supplier, partsupp, nation, region where p_partkey = ps_partkey and s_suppkey = ps_suppkey and p_size = ? and p_type like ? and s_nationkey = n_nationkey and n_regionkey = r_regionkey and r_name = ? and ps_supplycost = ( select min(ps_supplycost) from	partsupp,supplier,nation,region where p_partkey = ps_partkey and s_suppkey = ps_suppkey and s_nationkey = n_nationkey and n_regionkey = r_regionkey and r_name = 'EUROPE') order by s_acctbal desc, n_name, s_name, p_partkey LIMIT 100;";
    public static final String sql3 = "select l_orderkey, sum(l_extendedprice * (1 - l_discount)) as revenue, o_orderdate,o_shippriority from customer, orders, lineitem where c_mktsegment = ? and c_custkey = o_custkey and l_orderkey = o_orderkey and o_orderdate < ? and l_shipdate > ? group by l_orderkey, o_orderdate, o_shippriority order by revenue desc, o_orderdate LIMIT 10;";
    public static final String sql4 = " select o_orderpriority, count(*) as order_count from orders where o_orderdate >= ?::DATE and o_orderdate < ?::DATE + interval '3' month and exists (select * from lineitem where l_orderkey = o_orderkey and l_commitdate < l_receiptdate) group by o_orderpriority order by o_orderpriority LIMIT 1;";
    public static final String sql5 = " select n_name, sum(l_extendedprice * (1 - l_discount)) as revenue from customer,orders,lineitem,supplier,nation,region where c_custkey = o_custkey and l_orderkey = o_orderkey and l_suppkey = s_suppkey and c_nationkey = s_nationkey and s_nationkey = n_nationkey and n_regionkey = r_regionkey and r_name = ? and o_orderdate >= ? and o_orderdate < ?::DATE + interval '1' year group by n_name order by revenue desc LIMIT 1;";
    public static final String sql6 = " select\n" +
            "\tsum(l_extendedprice * l_discount) as revenue\n" +
            "from\n" +
            "\tlineitem\n" +
            "where\n" +
            "\tl_shipdate >= ? \n" +
            "\tand l_shipdate < ?::DATE + interval '1' year\n" +
            "\tand l_discount between ? - 0.01 and ? + 0.01\n" +
            "\tand l_quantity < ?\n" +
            "LIMIT 1;";
    public static final String sql7 = " select\n" +
            "\tsupp_nation,\n" +
            "\tcust_nation,\n" +
            "\tl_year,\n" +
            "\tsum(volume) as revenue\n" +
            "from\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tn1.n_name as supp_nation,\n" +
            "\t\t\tn2.n_name as cust_nation,\n" +
            "\t\t\textract(year from l_shipdate) as l_year,\n" +
            "\t\t\tl_extendedprice * (1 - l_discount) as volume\n" +
            "\t\tfrom\n" +
            "\t\t\tsupplier,\n" +
            "\t\t\tlineitem,\n" +
            "\t\t\torders,\n" +
            "\t\t\tcustomer,\n" +
            "\t\t\tnation n1,\n" +
            "\t\t\tnation n2\n" +
            "\t\twhere\n" +
            "\t\t\ts_suppkey = l_suppkey\n" +
            "\t\t\tand o_orderkey = l_orderkey\n" +
            "\t\t\tand c_custkey = o_custkey\n" +
            "\t\t\tand s_nationkey = n1.n_nationkey\n" +
            "\t\t\tand c_nationkey = n2.n_nationkey\n" +
            "\t\t\tand (\n" +
            "\t\t\t\t(n1.n_name = ? and n2.n_name = ?)\n" +
            "\t\t\t\tor (n1.n_name = ? and n2.n_name = ?)\n" +
            "\t\t\t)\n" +
            "\t\t\tand l_shipdate between date '1995-01-01' and date '1996-12-31'\n" +
            "\t) as shipping\n" +
            "group by\n" +
            "\tsupp_nation,\n" +
            "\tcust_nation,\n" +
            "\tl_year\n" +
            "order by\n" +
            "\tsupp_nation,\n" +
            "\tcust_nation,\n" +
            "\tl_year\n" +
            "LIMIT 1;";
    public static final String sql8 = " select\n" +
            "\to_year,\n" +
            "\tsum(case\n" +
            "\t\twhen nation = ? then volume\n" +
            "\t\telse 0\n" +
            "\tend) / sum(volume) as mkt_share\n" +
            "from\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\textract(year from o_orderdate) as o_year,\n" +
            "\t\t\tl_extendedprice * (1 - l_discount) as volume,\n" +
            "\t\t\tn2.n_name as nation\n" +
            "\t\tfrom\n" +
            "\t\t\tpart,\n" +
            "\t\t\tsupplier,\n" +
            "\t\t\tlineitem,\n" +
            "\t\t\torders,\n" +
            "\t\t\tcustomer,\n" +
            "\t\t\tnation n1,\n" +
            "\t\t\tnation n2,\n" +
            "\t\t\tregion\n" +
            "\t\twhere\n" +
            "\t\t\tp_partkey = l_partkey\n" +
            "\t\t\tand s_suppkey = l_suppkey\n" +
            "\t\t\tand l_orderkey = o_orderkey\n" +
            "\t\t\tand o_custkey = c_custkey\n" +
            "\t\t\tand c_nationkey = n1.n_nationkey\n" +
            "\t\t\tand n1.n_regionkey = r_regionkey\n" +
            "\t\t\tand r_name = ?\n" +
            "\t\t\tand s_nationkey = n2.n_nationkey\n" +
            "\t\t\tand o_orderdate between date '1995-01-01' and date '1996-12-31'\n" +
            "\t\t\tand p_type = ?\n" +
            "\t) as all_nations\n" +
            "group by\n" +
            "\to_year\n" +
            "order by\n" +
            "\to_year\n" +
            "LIMIT 1;";
    public static final String sql9 = " select\n" +
            "\tnation,\n" +
            "\to_year,\n" +
            "\tsum(amount) as sum_profit\n" +
            "from\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tn_name as nation,\n" +
            "\t\t\textract(year from o_orderdate) as o_year,\n" +
            "\t\t\tl_extendedprice * (1 - l_discount) - ps_supplycost * l_quantity as amount\n" +
            "\t\tfrom\n" +
            "\t\t\tpart,\n" +
            "\t\t\tsupplier,\n" +
            "\t\t\tlineitem,\n" +
            "\t\t\tpartsupp,\n" +
            "\t\t\torders,\n" +
            "\t\t\tnation\n" +
            "\t\twhere\n" +
            "\t\t\ts_suppkey = l_suppkey\n" +
            "\t\t\tand ps_suppkey = l_suppkey\n" +
            "\t\t\tand ps_partkey = l_partkey\n" +
            "\t\t\tand p_partkey = l_partkey\n" +
            "\t\t\tand o_orderkey = l_orderkey\n" +
            "\t\t\tand s_nationkey = n_nationkey\n" +
            "\t\t\tand p_name like ?\n" +
            "\t) as profit\n" +
            "group by\n" +
            "\tnation,\n" +
            "\to_year\n" +
            "order by\n" +
            "\tnation,\n" +
            "\to_year desc\n" +
            "LIMIT 1;";
    public static final String sql10 = " select\n" +
            "\tc_custkey,\n" +
            "\tc_name,\n" +
            "\tsum(l_extendedprice * (1 - l_discount)) as revenue,\n" +
            "\tc_acctbal,\n" +
            "\tn_name,\n" +
            "\tc_address,\n" +
            "\tc_phone,\n" +
            "\tc_comment\n" +
            "from\n" +
            "\tcustomer,\n" +
            "\torders,\n" +
            "\tlineitem,\n" +
            "\tnation\n" +
            "where\n" +
            "\tc_custkey = o_custkey\n" +
            "\tand l_orderkey = o_orderkey\n" +
            "\tand o_orderdate >= ? \n" +
            "\tand o_orderdate < ? + interval '3' month\n" +
            "\tand l_returnflag = 'R'\n" +
            "\tand c_nationkey = n_nationkey\n" +
            "group by\n" +
            "\tc_custkey,\n" +
            "\tc_name,\n" +
            "\tc_acctbal,\n" +
            "\tc_phone,\n" +
            "\tn_name,\n" +
            "\tc_address,\n" +
            "\tc_comment\n" +
            "order by\n" +
            "\trevenue desc\n" +
            "LIMIT 20;";
    public static final String sql11 = " select\n" +
            "\tps_partkey,\n" +
            "\tsum(ps_supplycost * ps_availqty) as value\n" +
            "from\n" +
            "\tpartsupp,\n" +
            "\tsupplier,\n" +
            "\tnation\n" +
            "where\n" +
            "\tps_suppkey = s_suppkey\n" +
            "\tand s_nationkey = n_nationkey\n" +
            "\tand n_name = ? \n" +
            "group by\n" +
            "\tps_partkey having\n" +
            "\t\tsum(ps_supplycost * ps_availqty) > (\n" +
            "\t\t\tselect\n" +
            "\t\t\t\tsum(ps_supplycost * ps_availqty) * ? \n" +
            "\t\t\tfrom\n" +
            "\t\t\t\tpartsupp,\n" +
            "\t\t\t\tsupplier,\n" +
            "\t\t\t\tnation\n" +
            "\t\t\twhere\n" +
            "\t\t\t\tps_suppkey = s_suppkey\n" +
            "\t\t\t\tand s_nationkey = n_nationkey\n" +
            "\t\t\t\tand n_name = ?\n" +
            "\t\t)\n" +
            "order by\n" +
            "\tvalue desc\n" +
            "LIMIT 1;";
    public static final String sql12 = " select\n" +
            "\tl_shipmode,\n" +
            "\tsum(case\n" +
            "\t\twhen o_orderpriority = '1-URGENT'\n" +
            "\t\t\tor o_orderpriority = '2-HIGH'\n" +
            "\t\t\tthen 1\n" +
            "\t\telse 0\n" +
            "\tend) as high_line_count,\n" +
            "\tsum(case\n" +
            "\t\twhen o_orderpriority <> '1-URGENT'\n" +
            "\t\t\tand o_orderpriority <> '2-HIGH'\n" +
            "\t\t\tthen 1\n" +
            "\t\telse 0\n" +
            "\tend) as low_line_count\n" +
            "from\n" +
            "\torders,\n" +
            "\tlineitem\n" +
            "where\n" +
            "\to_orderkey = l_orderkey\n" +
            "\tand l_shipmode in (?, '?)\n" +
            "\tand l_commitdate < l_receiptdate\n" +
            "\tand l_shipdate < l_commitdate\n" +
            "\tand l_receiptdate >= ? \n" +
            "\tand l_receiptdate < ? + interval '1' year\n" +
            "group by\n" +
            "\tl_shipmode\n" +
            "order by\n" +
            "\tl_shipmode\n" +
            "LIMIT 1;";
    public static final String sql13 = " select\n" +
            "\tc_count,\n" +
            "\tcount(*) as custdist\n" +
            "from\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tc_custkey,\n" +
            "\t\t\tcount(o_orderkey)\n" +
            "\t\tfrom\n" +
            "\t\t\tcustomer left outer join orders on\n" +
            "\t\t\t\tc_custkey = o_custkey\n" +
            "\t\t\t\tand o_comment not like ?\n" +
            "\t\tgroup by\n" +
            "\t\t\tc_custkey\n" +
            "\t) as c_orders (c_custkey, c_count)\n" +
            "group by\n" +
            "\tc_count\n" +
            "order by\n" +
            "\tcustdist desc,\n" +
            "\tc_count desc\n" +
            "LIMIT 1;";
    public static final String sql14 = " select\n" +
            "\t100.00 * sum(case\n" +
            "\t\twhen p_type like 'PROMO%'\n" +
            "\t\t\tthen l_extendedprice * (1 - l_discount)\n" +
            "\t\telse 0\n" +
            "\tend) / sum(l_extendedprice * (1 - l_discount)) as promo_revenue\n" +
            "from\n" +
            "\tlineitem,\n" +
            "\tpart\n" +
            "where\n" +
            "\tl_partkey = p_partkey\n" +
            "\tand l_shipdate >= ?\n" +
            "\tand l_shipdate < ?::DATE + interval '1' month\n" +
            "LIMIT 1;";
    public static final String sql15 = "create view revenue0 (supplier_no, total_revenue) as\n" +
            "\tselect\n" +
            "\t\tl_suppkey,\n" +
            "\t\tsum(l_extendedprice * (1 - l_discount))\n" +
            "\tfrom\n" +
            "\t\tlineitem\n" +
            "\twhere\n" +
            "\t\tl_shipdate >= ?\n" +
            "\t\tand l_shipdate < ?::DATE + interval '3' month\n" +
            "\tgroup by\n" +
            "\t\tl_suppkey;\n" +
            "\n" +
            "\n" +
            " select\n" +
            "\ts_suppkey,\n" +
            "\ts_name,\n" +
            "\ts_address,\n" +
            "\ts_phone,\n" +
            "\ttotal_revenue\n" +
            "from\n" +
            "\tsupplier,\n" +
            "\trevenue0\n" +
            "where\n" +
            "\ts_suppkey = supplier_no\n" +
            "\tand total_revenue = (\n" +
            "\t\tselect\n" +
            "\t\t\tmax(total_revenue)\n" +
            "\t\tfrom\n" +
            "\t\t\trevenue0\n" +
            "\t)\n" +
            "order by\n" +
            "\ts_suppkey\n" +
            "LIMIT 1; drop view revenue0;";
    public static final String sql16 = " select\n" +
            "\tp_brand,\n" +
            "\tp_type,\n" +
            "\tp_size,\n" +
            "\tcount(distinct ps_suppkey) as supplier_cnt\n" +
            "from\n" +
            "\tpartsupp,\n" +
            "\tpart\n" +
            "where\n" +
            "\tp_partkey = ps_partkey\n" +
            "\tand p_brand <> ? \n" +
            "\tand p_type not like ?\n" +
            "\tand p_size in (?, ?, ?, ?, ?, ?, ?, ?)\n" +
            "\tand ps_suppkey not in (\n" +
            "\t\tselect\n" +
            "\t\t\ts_suppkey\n" +
            "\t\tfrom\n" +
            "\t\t\tsupplier\n" +
            "\t\twhere\n" +
            "\t\t\ts_comment like '%Customer%Complaints%'\n" +
            "\t)\n" +
            "group by\n" +
            "\tp_brand,\n" +
            "\tp_type,\n" +
            "\tp_size\n" +
            "order by\n" +
            "\tsupplier_cnt desc,\n" +
            "\tp_brand,\n" +
            "\tp_type,\n" +
            "\tp_size\n" +
            "LIMIT 1;";
    public static final String sql17 = " select sum(l_extendedprice) / 7.0 as avg_yearly from\n" +
            "\tlineitem,\n" +
            "\tpart,\n" +
            "\t(SELECT l_partkey AS agg_partkey, 0.2 * avg(l_quantity) AS avg_quantity FROM lineitem GROUP BY l_partkey) part_agg\n" +
            "where\n" +
            "\tp_partkey = l_partkey\n" +
            "\tand agg_partkey = l_partkey\n" +
            "\tand p_brand = ?\n" +
            "\tand p_container = ?\n" +
            "\tand l_quantity < avg_quantity\n" +
            "LIMIT 1;";
    public static final String sql18 = " select\n" +
            "\tc_name,\n" +
            "\tc_custkey,\n" +
            "\to_orderkey,\n" +
            "\to_orderdate,\n" +
            "\to_totalprice,\n" +
            "\tsum(l_quantity)\n" +
            "from\n" +
            "\tcustomer,\n" +
            "\torders,\n" +
            "\tlineitem\n" +
            "where\n" +
            "\to_orderkey in (\n" +
            "\t\tselect\n" +
            "\t\t\tl_orderkey\n" +
            "\t\tfrom\n" +
            "\t\t\tlineitem\n" +
            "\t\tgroup by\n" +
            "\t\t\tl_orderkey having\n" +
            "\t\t\t\tsum(l_quantity) > ?\n" +
            "\t)\n" +
            "\tand c_custkey = o_custkey\n" +
            "\tand o_orderkey = l_orderkey\n" +
            "group by\n" +
            "\tc_name,\n" +
            "\tc_custkey,\n" +
            "\to_orderkey,\n" +
            "\to_orderdate,\n" +
            "\to_totalprice\n" +
            "order by\n" +
            "\to_totalprice desc,\n" +
            "\to_orderdate\n" +
            "LIMIT 100;";
    public static final java.lang.String sql19 = " select\n" +
            "\tsum(l_extendedprice* (1 - l_discount)) as revenue\n" +
            "from\n" +
            "\tlineitem,\n" +
            "\tpart\n" +
            "where\n" +
            "\t(\n" +
            "\t\tp_partkey = l_partkey\n" +
            "\t\tand p_brand = ?\n" +
            "\t\tand p_container in ('SM CASE', 'SM BOX', 'SM PACK', 'SM PKG')\n" +
            "\t\tand l_quantity >= ? and l_quantity <= ? + 10\n" +
            "\t\tand p_size between 1 and 5\n" +
            "\t\tand l_shipmode in ('AIR', 'AIR REG')\n" +
            "\t\tand l_shipinstruct = 'DELIVER IN PERSON'\n" +
            "\t)\n" +
            "\tor\n" +
            "\t(\n" +
            "\t\tp_partkey = l_partkey\n" +
            "\t\tand p_brand = ?\n" +
            "\t\tand p_container in ('MED BAG', 'MED BOX', 'MED PKG', 'MED PACK')\n" +
            "\t\tand l_quantity >= ? and l_quantity <= ? + 10\n" +
            "\t\tand p_size between 1 and 10\n" +
            "\t\tand l_shipmode in ('AIR', 'AIR REG')\n" +
            "\t\tand l_shipinstruct = 'DELIVER IN PERSON'\n" +
            "\t)\n" +
            "\tor\n" +
            "\t(\n" +
            "\t\tp_partkey = l_partkey\n" +
            "\t\tand p_brand = ?\n" +
            "\t\tand p_container in ('LG CASE', 'LG BOX', 'LG PACK', 'LG PKG')\n" +
            "\t\tand l_quantity >= ? and l_quantity <= ? + 10\n" +
            "\t\tand p_size between 1 and 15\n" +
            "\t\tand l_shipmode in ('AIR', 'AIR REG')\n" +
            "\t\tand l_shipinstruct = 'DELIVER IN PERSON'\n" +
            "\t)\n" +
            "LIMIT 1; ";
    public static final java.lang.String sql20 = " select\n" +
            "\ts_name,\n" +
            "\ts_address\n" +
            "from\n" +
            "\tsupplier,\n" +
            "\tnation\n" +
            "where\n" +
            "\ts_suppkey in (\n" +
            "\t\tselect\n" +
            "\t\t\tps_suppkey\n" +
            "\t\tfrom\n" +
            "\t\t\tpartsupp,\n" +
            "\t\t\t(\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tl_partkey agg_partkey,\n" +
            "\t\t\t\t\tl_suppkey agg_suppkey,\n" +
            "\t\t\t\t\t0.5 * sum(l_quantity) AS agg_quantity\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tlineitem\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tl_shipdate >= ?\n" +
            "\t\t\t\t\tand l_shipdate < ?::DATE + interval '1' year\n" +
            "\t\t\t\tgroup by\n" +
            "\t\t\t\t\tl_partkey,\n" +
            "\t\t\t\t\tl_suppkey\n" +
            "\t\t\t) agg_lineitem\n" +
            "\t\twhere\n" +
            "\t\t\tagg_partkey = ps_partkey\n" +
            "\t\t\tand agg_suppkey = ps_suppkey\n" +
            "\t\t\tand ps_partkey in (\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tp_partkey\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tpart\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tp_name like ?\n" +
            "\t\t\t)\n" +
            "\t\t\tand ps_availqty > agg_quantity\n" +
            "\t)\n" +
            "\tand s_nationkey = n_nationkey\n" +
            "\tand n_name = ?\n" +
            "order by\n" +
            "\ts_name\n" +
            "LIMIT 1;";
    public static final String sql21 = " select\n" +
            "\ts_name,\n" +
            "\tcount(*) as numwait\n" +
            "from\n" +
            "\tsupplier,\n" +
            "\tlineitem l1,\n" +
            "\torders,\n" +
            "\tnation\n" +
            "where\n" +
            "\ts_suppkey = l1.l_suppkey\n" +
            "\tand o_orderkey = l1.l_orderkey\n" +
            "\tand o_orderstatus = 'F'\n" +
            "\tand l1.l_receiptdate > l1.l_commitdate\n" +
            "\tand exists (\n" +
            "\t\tselect\n" +
            "\t\t\t*\n" +
            "\t\tfrom\n" +
            "\t\t\tlineitem l2\n" +
            "\t\twhere\n" +
            "\t\t\tl2.l_orderkey = l1.l_orderkey\n" +
            "\t\t\tand l2.l_suppkey <> l1.l_suppkey\n" +
            "\t)\n" +
            "\tand not exists (\n" +
            "\t\tselect\n" +
            "\t\t\t*\n" +
            "\t\tfrom\n" +
            "\t\t\tlineitem l3\n" +
            "\t\twhere\n" +
            "\t\t\tl3.l_orderkey = l1.l_orderkey\n" +
            "\t\t\tand l3.l_suppkey <> l1.l_suppkey\n" +
            "\t\t\tand l3.l_receiptdate > l3.l_commitdate\n" +
            "\t)\n" +
            "\tand s_nationkey = n_nationkey\n" +
            "\tand n_name = ?\n" +
            "group by\n" +
            "\ts_name\n" +
            "order by\n" +
            "\tnumwait desc,\n" +
            "\ts_name\n" +
            "LIMIT 100;";
    public static final java.lang.String sql22 = " select\n" +
            "\tcntrycode,\n" +
            "\tcount(*) as numcust,\n" +
            "\tsum(c_acctbal) as totacctbal\n" +
            "from\n" +
            "\t(\n" +
            "\t\tselect\n" +
            "\t\t\tsubstring(c_phone from 1 for 2) as cntrycode,\n" +
            "\t\t\tc_acctbal\n" +
            "\t\tfrom\n" +
            "\t\t\tcustomer\n" +
            "\t\twhere\n" +
            "\t\t\tsubstring(c_phone from 1 for 2) in\n" +
            "\t\t\t\t('?', '?', '?', '?', '?', '?', '?')\n" +
            "\t\t\tand c_acctbal > (\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\tavg(c_acctbal)\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\tcustomer\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\tc_acctbal > 0.00\n" +
            "\t\t\t\t\tand substring(c_phone from 1 for 2) in\n" +
            "\t\t\t\t\t\t('?', '?', '?', '?', '?', '?', '?')\n" +
            "\t\t\t)\n" +
            "\t\t\tand not exists (\n" +
            "\t\t\t\tselect\n" +
            "\t\t\t\t\t*\n" +
            "\t\t\t\tfrom\n" +
            "\t\t\t\t\torders\n" +
            "\t\t\t\twhere\n" +
            "\t\t\t\t\to_custkey = c_custkey\n" +
            "\t\t\t)\n" +
            "\t) as custsale\n" +
            "group by\n" +
            "\tcntrycode\n" +
            "order by\n" +
            "\tcntrycode\n" +
            "LIMIT 1;";
}
