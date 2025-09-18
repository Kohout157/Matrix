package org.example;

public class Main {
    public static void main(String[] args) {
        Matrix matrixA = new Matrix(3, 3);
        Matrix matrixB = new Matrix(3, 3);
        Matrix matrixC = new Matrix(4, 4);
        double[][] dataA = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        double[][] dataB = {
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        };
        double[][] dataC = {{1, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 3, 0},
                {0, 0, 0, 4}};
        matrixC.setData(dataC);
        matrixA.setData(dataA);
        matrixB.setData(dataB);

        IMatrix transpose = matrixA.transpose();
        System.out.println("Transpose of A:");
        transpose.printMatrix();

        System.out.println(matrixA.isSquare());
        System.out.println(matrixB.isDiagonal());

        IMatrix result = matrixA.add(matrixB);
        System.out.println("Result of A + B:");
        result.printMatrix();

        IMatrix result2 = matrixA.times(matrixB);
        System.out.println("Result of A * B:");
        result2.printMatrix();

        IMatrix result3 = matrixA.times(2);
        System.out.println("Result of A * 2:");
        result3.printMatrix();

        System.out.println("Determinant of A: " + matrixC.determinant());
        System.out.println("Trace of C: " + matrixC.getTrace());
    }
}