package com.tsystems.javaschool.tasks.pyramid;

import java.util.*;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        //validates input list and throws exception:
        validateInputNumbers(inputNumbers);

        //calculates height, throws exception if list length is invalid:
        int height = calculateHeight(inputNumbers);

        //creates empty matrix:
        int[][] matrix = new int[height][height * 2 - 1];
        Collections.sort(inputNumbers);

        //fills the matrix with sorted list of numbers:
        fillPyramid(matrix, inputNumbers);

        return matrix;
    }

    private void fillPyramid(int[][] matrix, List<Integer> inputNumbers) {
        if (matrix.length == 0
                || matrix[0].length == 0
                || matrix[0].length != matrix.length * 2 - 1) {
            throw new IllegalStateException("The matrix for placing a pyramid was created incorrectly");
        }
        int height = matrix.length;
        int width = matrix[0].length;
        int digitsPerRow = 1;
        int currentIndex = 0;

        int floorStart;
        int floorEnd;

        for (int i = 0; i < height; i++) {
            floorStart = (width - (digitsPerRow + digitsPerRow - 1)) / 2;
            floorEnd = floorStart + digitsPerRow + digitsPerRow - 1;

            for (int j = floorStart; j < floorEnd; j += 2) {
                matrix[i][j] = inputNumbers.get(currentIndex);
                currentIndex++;
            }
            digitsPerRow++;
        }

    }


    private int calculateHeight(List<Integer> inputNumbers) {
        int numberOfDigits = inputNumbers.size();
        int height = 0;
        int digitsPerRow = 1;

        while (numberOfDigits > 0) {
            if (numberOfDigits >= digitsPerRow) {
                height++;
                numberOfDigits -= digitsPerRow;
                digitsPerRow++;
            } else {
                String msg = String.format("Input number list of length %d is not sufficient to build a pyramid", inputNumbers.size());
                throw new CannotBuildPyramidException(msg);
            }
        }
        return height;
    }

    private void validateInputNumbers(List<Integer> inputNumbers) {
        if (inputNumbers == null || inputNumbers.size() == 0) {
            throw new CannotBuildPyramidException("Input number list should not be null or empty");
        }
        for (Integer element : inputNumbers) {
            if (null == element) {
                throw new CannotBuildPyramidException("Input number list should not contain nulls");
            }
        }
    }

}
