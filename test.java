package com.excelsoft;

import java.util.ArrayList;
import java.util.Arrays;

public class test {

    public static void main(String[] args) {
        double[] data = { 65, 12, 11, 17, 16, 20, 23, 25, -15, 30, 33, 34, 35,36, 37 };
        double[] result = boxPlot(data);
        System.out.println("处理后:" + Arrays.toString(result));
    }
    public static double[] boxPlot(double[] data) {
        ArrayList<Integer> removeList = new ArrayList<Integer>();
        ArrayList<Double> dataList = new ArrayList<Double>();
        double[] dataClone = data.clone();
        Arrays.sort(data);
        sort(data, 0, data.length - 1);
        // 中位数
        double index2 = ((double) data.length + 1.00) / 2;
        double Q2 = ((int) index2 + 1 - index2) * data[(int) index2 - 1]	+ (index2 - (int) index2) * data[(int) index2];
        // 下四分位数
        double index1 = ((double) data.length + 1.00) / 4;
        double Q1 = ((int) index1 + 1 - index1) * data[(int) index1 - 1]	+ (index1 - (int) index1) * data[(int) index1];
        // 上四分位数
        double index3 = ((double) data.length + 1.00) * 3 / 4;
        double Q3 = ((int) index3 + 1 - index3) * data[(int) index3 - 1]	+ (index3 - (int) index3) * data[(int) index3];
        double IQR = Q3 - Q1;
        double k = 1.5;
        double upperLimit = Q3 + k * IQR;// 上限
        double lowerLimit = Q1 - k * IQR;// 下限
        System.out.println("上限uLim:" + upperLimit + "\n上四分位数Q3:" + Q3	+ "\n中位数（第二个四分位数）Q2:" + Q2 + "\n下四分位数Q1:" + Q1 + "\n下限lLim:"+ lowerLimit);// +"\n四分位距IQR:"+IQR
        for (int i = 0; i < dataClone.length; i++) {
            dataList.add(dataClone[i]);
        }
        for (int i = dataList.size() - 1; i >= 0; i--) {
            double tmp = dataList.get(i);
            if (tmp > upperLimit || tmp < lowerLimit) {

                removeList.add(i);// 添加删除的index,index从大到小,5,4,1
                dataList.remove(i);
            }
        }
        // 求清洗后剩下的数的均值
        // double sum = 0;
        // for(int i=0;i<dataClone.length;i++){
        // sum += dataClone[i];
        // }
        // double ave = sum/(dataClone.length-removeList.size());
        /*
         * 删除后需要补上元素，我采取的是补上待插入位置相邻两个数的中位数。或许可以补上均值？
         */
        if (removeList.size() > 0) {
            // 补上元素，倒序 ，index从小到大的添加,1,3,5
            for (int i = removeList.size() - 1; i >= 0; i--) {
                int tmp = removeList.get(i);// index
                if (tmp == dataList.size()) {// 说明删除的是最后一个元素
                    double addElem = dataList.get(tmp - 1);// 补上前一个元素
                    System.out.println("index:" + tmp + "," + dataClone[tmp]);
                    dataList.add(tmp, addElem);
                } else if (tmp == 0) {// 说明删除的是第一个元素
                    double addElem = dataList.get(tmp);// 补上后一个元素
                    System.out.println("index:" + tmp + "," + dataClone[tmp]);
                    dataList.add(tmp, addElem);
                } else {
                    double addElem = dataList.get(tmp - 1) + dataList.get(tmp);
                    System.out.println("index:" + tmp + "," + dataClone[tmp]);
                    addElem /= 2;
                    dataList.add(tmp, addElem);
                }
            }
        }
        double[] res = new double[dataList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = dataList.get(i);
        }
        System.out.println("原数组:" + Arrays.toString(dataClone) + "\n共发现"	+ removeList.size() + "个异常\n位置分别在:"	+ Arrays.toString(removeList.toArray()));
        return res;
    }
    public static double[] sort(double[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            sort(a, low, mid);
            sort(a, mid + 1, high);
            // 左右归并
            merge(a, low, mid, high);
        }
        return a;
    }
    public static void merge(double[] a, int low, int mid, int high) {
        double[] temp = new double[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int x = 0; x < temp.length; x++) {
            a[x + low] = temp[x];
        }
    }
}
