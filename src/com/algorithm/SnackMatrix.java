package com.algorithm;

import java.util.Scanner;

/**
 * 蛇形矩阵
 * 
 * @author copy4dev
 * @date 2016年9月3日
 *
 */
public class SnackMatrix {
	int n;

	public SnackMatrix(int n) {
		this.n = n;
	}

	public void print() {
		int[][] data = new int[n][n];
		data[0][0] = 1;
		data[n - 1][n - 1] = n * n;

		// 将蛇形矩阵按照正对角线分为上半部分和下半部分
		// 现在先来设计上半部分，并且负责对角线，上半部分可将斜线的顺序记为k,按k的奇偶性进行判断
		for (int k = 1; k <= (n - 1); k++) {
			if (k % 2 == 1) {// 当k为奇数时，代表每条斜线的最小值在上方
				data[0][k] = 1 + k * (k + 1) / 2;
				for (int i = 1; i <= k; i++) {
					data[i][k - i] = data[0][k] + i;// 行递增，列递减

				}
			} else {// 当k为偶数时，代表每条斜线的最小值在下方
				data[k][0] = 1 + k * (k + 1) / 2;
				for (int i = 0; i <= k; i++) {
					data[k - i][i] = data[k][0] + i; // 行递减，列递增

				}
			}
		}// 上半部分就已经设计好了，接着设计下半部分

		// 下半部分就会显得比较复杂，首先要先判断n的奇偶性，还要再判断k的奇偶性
		// 从左向右按照从大到小的顺序进行斜线的连接，同样以k代表斜线的序号
		if (n % 2 == 0) {// 如果n为偶数
			for (int k = 1; k <= (n - 2); k++) {
				if (k % 2 == 1) {// 当k为奇数的时候每条斜线的最大值在上方
					data[k][n - 1] = data[n - 1][n - 1] - (n - k - 1) * (n - k) / 2;
					for (int i = 1; i < n - k; i++) {
						data[k + i][n - 1 - i] = data[k][n - 1] - i;// 行递增，列递减
					}
				} else {// 当k为偶数的时候，每条斜线的最大值在下方
					data[n - 1][k] = data[n - 1][n - 1] - (n - k - 1) * (n - k) / 2;
					for (int i = 1; i < n - k; i++) {
						data[n - 1 - i][k + i] = data[n - 1][k] - i;// 行递减，列递增
					}
				}
			}
		} else {// 如果n为奇数，那么就是相反的
			for (int k = 1; k <= (n - 2); k++) {
				if (k % 2 == 0) {// 当k为偶数的时候每条斜线的最大值在上方
					data[k][n - 1] = data[n - 1][n - 1] - (n - k - 1) * (n - k) / 2;
					for (int i = 1; i < n - k; i++) {
						data[k + i][n - 1 - i] = data[k][n - 1] - i;// 行递增，列递减
					}
				} else {// 当k为奇数的时候，每条斜线的最大值在下方
					data[n - 1][k] = data[n - 1][n - 1] - (n - k - 1) * (n - k) / 2;
					for (int i = 1; i < n - k; i++) {
						data[n - 1 - i][k + i] = data[n - 1][k] - i;// 行递减，列递增
					}
				}
			}
		}// 下半部分的就设计好咯

		// 接下来就是显示矩阵咯

		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}// 结束显示，结束print方法，进入main方法
	}// 结束构造方法

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		for (int i = 1; i < 4; i++) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入蛇形矩阵的阶数：");
			int number = scanner.nextInt();
			SnackMatrix snake = new SnackMatrix(number);
			snake.print();
		}
	}

}