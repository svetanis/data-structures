package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 2408. Design SQL
//
// The constructor takes the table names and each table's column count. ins(name, row) adds a
// row with the table's next id (1, 2, 3, ...) and returns true, or returns false when the
// table does not exist or the row has the wrong number of cells. rmv(name, rowId) deletes a
// row, doing nothing when it does not exist. sel(name, rowId, columnId) returns one cell,
// columns counted from 1, or "<null>" when the table, row or column does not exist -- a
// column below 1 included, since the statement gives columnId no range.
// exp(name) returns every row as "rowId,cell1,cell2,...", or an empty list for no table.
//
// One map from table name to a Table, and each Table keeps its column count, its next id and
// a map from row id to row. The next id only ever goes up, so the id of a deleted row is never
// handed out again: rows keep the ids they were given. The row map is a LinkedHashMap, so exp
// lists rows in the order they were inserted, which is increasing id order because ids only
// go up -- as the statement's examples show. A plain HashMap lists them in its bucket order,
// which is by id only while every id is smaller than its table: a table that never held more
// than two rows at once, left with ids 15 and 17, listed 17 first.
//
// Siblings, same problem: none in this package.

public final class SQL {
	// Time complexity: ins, rmv, sel O(1) on average, map lookups; exp O(r * c), every cell of
	//   r rows of c cells
	// Auxiliary Space: O(total cells), the rows as given

	private Map<String, Table> tables; // name -> its Table

	public SQL(List<String> names, List<Integer> columns) {
		int size = names.size();
		this.tables = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			this.tables.put(names.get(i), new Table(columns.get(i)));
		}
	}

	public boolean ins(String name, List<String> row) {
		Table table = tables.get(name);
		if (table == null || table.cols != row.size()) { // FITS? the table exists, the width matches
			return false;
		}
		table.rows.put(table.autoIncIndex++, row); // ID: the next one, never reused
		return true;
	}

	public void rmv(String name, int rowId) {
		Table table = tables.get(name);
		if (table == null) {
			return;
		}
		table.rows.remove(rowId); // DROP: the id goes with it
	}

	public String sel(String name, int rowId, int columnId) {
		Table table = tables.get(name);
		if (table == null) {
			return "<null>";
		}
		List<String> row = table.rows.get(rowId);
		if (row == null || columnId < 1 || columnId > row.size()) { // CELL? the row exists, the column is inside it
			return "<null>";
		}
		return row.get(columnId - 1); // CELL: columns count from 1
	}

	public List<String> exp(String name) {
		Table table = tables.get(name);
		if (table == null) {
			return List.of();
		}
		List<String> list = new ArrayList<>();
		for (int rowId : table.rows.keySet()) { // ROW: in insertion order, which is id order
			list.add(rowId + "," + String.join(",", table.rows.get(rowId)));
		}
		return list;
	}

	public static void main(String[] args) {
		// Creates three tables.
		SQL sql = new SQL(Arrays.asList("one", "two", "three"), Arrays.asList(2, 3, 1));

		// Adds a row to the table "two" with id 1. Returns True.
		sql.ins("two", Arrays.asList("first", "second", "third"));

		// Returns the value "third" from the third column
		// in the row with id 1 of the table "two".
		sql.sel("two", 1, 3);

		// Adds another row to the table "two" with id 2. Returns True.
		sql.ins("two", Arrays.asList("fourth", "fifth", "sixth"));

		// Exports the rows of the table "two".
		// Currently, the table has 2 rows with ids 1 and 2.
		sql.exp("two");

		// Removes the first row of the table "two". Note that the second row
		// will still have the id 2.
		sql.rmv("two", 1);

		// Returns the value "fifth" from the second column
		// in the row with id 2 of the table "two".
		sql.sel("two", 2, 2);

		// Exports the rows of the table "two".
		// Currently, the table has 1 row with id 2.
		sql.exp("two");

		// Creates three tables.
		SQL sql2 = new SQL(Arrays.asList("one", "two", "three"), Arrays.asList(2, 3, 1));

		// Adds a row to the table "two" with id 1. Returns True.
		sql2.ins("two", Arrays.asList("first", "second", "third"));

		// Returns the value "third" from the third column
		// in the row with id 1 of the table "two".
		sql2.sel("two", 1, 3);

		// Removes the first row of the table "two".
		sql2.rmv("two", 1);

		// Returns "<null>" as the cell with id 1
		// has been removed from table "two".
		sql2.sel("two", 1, 2);

		// Returns False as number of columns are not correct.
		sql2.ins("two", Arrays.asList("fourth", "fifth"));

		// Adds a row to the table "two" with id 2. Returns True.
		sql2.ins("two", Arrays.asList("fourth", "fifth", "sixth"));
	}

	private static class Table {
		private int cols; // how many cells every row must have
		private int autoIncIndex; // the id the next row gets
		private Map<Integer, List<String>> rows; // row id -> row, in insertion (= id) order

		public Table(int cols) {
			this.autoIncIndex = 1;
			this.cols = cols;
			this.rows = new LinkedHashMap<>(); // ORDER: exp lists rows by increasing id
		}
	}
}