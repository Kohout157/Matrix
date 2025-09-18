package org.example;

public class Matrix implements IMatrix {

    private int rows;
    private int columns;
    private double[][] data;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows][columns];
    }

    public void setData(double[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0) {
            throw new IllegalArgumentException("Data nesmí být prázdná.");
        }
        int cols = data[0].length;
        for (double[] row : data) {
            if (row.length != cols) {
                throw new IllegalArgumentException("Všechny řádky musí mít stejný počet sloupců.");
            }
        }
        this.data = data;
        this.rows = data.length;
        this.columns = cols;
    }


    @Override
    public IMatrix times(IMatrix matrix) {
        if (this.columns != matrix.getRows()) {
            throw new IllegalArgumentException("Počet sloupců první matice musí být roven počtu řádků druhé matice.");
        }
        Matrix result = new Matrix(this.rows, matrix.getColumns());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                for (int k = 0; k < this.columns; k++) {
                    result.data[i][j] += this.data[i][k] * matrix.get(k, j);
                }
            }
        }
        return result;
    }

    @Override
    public IMatrix times(int scalar) {
        Matrix matrix = new Matrix(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix.data[i][j] = this.data[i][j] * scalar;
            }
        }
        return matrix;
    }


    @Override
    public IMatrix add(IMatrix matrix) {
        if (this.rows != matrix.getRows() || this.columns != matrix.getColumns()) {
            throw new IllegalArgumentException("Matice musí mít stejné rozměry.");
        }
        Matrix result = new Matrix(this.rows, this.columns);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                result.data[i][j] = this.data[i][j] + matrix.get(i, j);
            }
        }
        return result;
    }

    @Override
    public IMatrix transpose() {
        Matrix transposed = new Matrix(this.columns, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                transposed.data[j][i] = this.data[i][j];
            }
        }
        return transposed;
    }

    @Override
    public double determinant() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Determinant lze počítat jen pro čtvercovou matici.");
        }
        return computeDeterminant(this.data);
    }

    private double computeDeterminant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        double det = 0;
        for (int col = 0; col < n; col++) {
            det += Math.pow(-1, col) * matrix[0][col] * computeDeterminant(minor(matrix, 0, col));
        }
        return det;
    }

    private double[][] minor(double[][] matrix, int rowToRemove, int colToRemove) {
        int n = matrix.length;
        double[][] minor = new double[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == rowToRemove) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == colToRemove) continue;
                minor[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return minor;
    }


    @Override
    public boolean isSquare() {
        return this.rows == this.columns;
    }

    @Override
    public boolean isDiagonal() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                if (i != j && this.data[i][j] != 0) {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Number getTrace() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Trace lze počítat jen pro čtvercovou matici.");
        }
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            sum += data[i][i];
        }
        return sum;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getColumns() {
        return this.columns;
    }

    @Override
    public double get(int row, int col) {
        return data[row][col];
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}
