package com.viger.mycode.shejimoshi;

/**
 * 责任链模式test1
 */
public class zerenlian {

    public static void main(String[] args) {
        int price = 240;
        String productStr = String.format("商品清单：苹果、香蕉、桔子, 商品总金额为：%d元.", price);
        System.out.println(productStr);
        //声明责任链上的所有节点
        FullDiscountFilter fulDF = new FullDiscountFilter();
        FirstPurchaseDiscount firstDF = new FirstPurchaseDiscount();
        SecondPurchaseDiscountFilter secDF = new SecondPurchaseDiscountFilter();
        HolidayDiscountFilter holDF = new HolidayDiscountFilter();
        //设置链中的顺序:满减->首购减->第二件减->假日减
        fulDF.nextDiscountFilter = firstDF;
        firstDF.nextDiscountFilter = secDF;
        secDF.nextDiscountFilter = holDF;
        holDF.nextDiscountFilter = null;
        int total = fulDF.calculateBySourcePrice(price);
        System.out.println(String.format("所有商品优惠价后金额为:%d", total));
    }

    //折扣优惠接口
    static abstract class DiscountFilter {

        protected DiscountFilter nextDiscountFilter;

        public abstract int calculateBySourcePrice(int price);

    }

    //满200减20元
    public static  class FullDiscountFilter extends DiscountFilter {
        @Override
        public int calculateBySourcePrice(int price) {
            if(price >= 200) {
                System.out.println("优惠满减20元");
                price -= 20;
            }
            if(this.nextDiscountFilter != null) {
                return super.nextDiscountFilter.calculateBySourcePrice(price);
            }
            System.out.println("calculateBySourcePrice:"+price);
            return price;
        }
    }

    //首次购买减20元
    public static  class FirstPurchaseDiscount extends DiscountFilter {
        @Override
        public int calculateBySourcePrice(int price) {
            if(price > 100) {
                System.out.println("首次购买减20元");
                price -= 20;
            }
            if(this.nextDiscountFilter != null) {
                return super.nextDiscountFilter.calculateBySourcePrice(price);
            }
            System.out.println("calculateBySourcePrice:"+price);
            return price;
        }
    }

    //第二件打9折
    public static  class SecondPurchaseDiscountFilter  extends DiscountFilter{
        public int calculateBySourcePrice(int price) {
            System.out.println("第二件打9折");
            Double balance =  price * 0.9;
            if(this.nextDiscountFilter != null) {
                return super.nextDiscountFilter.calculateBySourcePrice(balance.intValue());
            }
            System.out.println("SecondPurchaseDiscountFilter:"+price);
            return price;
        }
    }

    //节日一律减5元
    public static  class HolidayDiscountFilter extends DiscountFilter{
        public int calculateBySourcePrice(int price) {
            if (price > 20){
                System.out.println("节日一律减5元");
                price = price - 5;
            }
            if(this.nextDiscountFilter != null) {
                return super.nextDiscountFilter.calculateBySourcePrice(price);
            }
            System.out.println("HolidayDiscountFilter:"+price);
            return price;
        }
    }


}
