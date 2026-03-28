package com.svetanis.datastructures.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 2408. Design SQL

public final class SQL {

	private Map<String, Table> tables;

	public SQL(List<String> names, List<Integer> columns) {
		int size = names.size();
		this.tables = new HashMap<>(size);
		for (int i = 0; i < size; i++) {
			this.tables.put(names.get(i), new Table(columns.get(i)));
		}
	}

	public boolean ins(String name, List<String> row) {
		Table table = tables.get(name);
		if (table == null || table.cols != row.size()) {
			return false;
		}
		table.rows.put(table.autoIncIndex++, row);
		return true;
	}

	public void rmv(String name, int rowId) {
		Table table = tables.get(name);
		if (table == null) {
			return;
		}
		table.rows.remove(rowId);
	}

	public String sel(String name, int rowId, int columnId) {
		Table table = tables.get(name);
		if (table == null) {
			return "<null>";
		}
		List<String> row = table.rows.get(rowId);
		if (row == null || columnId > row.size()) {
			return "<null>";
		}
		return row.get(columnId - 1);
	}

	public List<String> exp(String name) {
		Table table = tables.get(name);
		if (table == null) {
			return List.of();
		}
		List<String> list = new ArrayList<>();
		for (int rowId : table.rows.keySet()) {
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
		private int cols;
		private int autoIncIndex;
		private Map<Integer, List<String>> rows;

		public Table(int cols) {
			this.autoIncIndex = 1;
			this.cols = cols;
			this.rows = new HashMap<>();
		}
	}
}