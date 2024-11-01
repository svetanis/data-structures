package com.svetanis.datastructures.tree.implicit;

import static com.google.common.base.Optional.absent;
import static com.svetanis.java.base.Optionals.present;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Optional;

public final class NestedInteger {

	public NestedInteger() {
		this(absent());
	}

	public NestedInteger(int value) {
		this(present(value));
	}

	private NestedInteger(Optional<Integer> value) {
		this.value = value;
		this.nested = new ArrayList<>();
	}

	private Optional<Integer> value;
	private List<NestedInteger> nested;

	public boolean isInteger() {
		return value.isPresent();
	}

	public void add(NestedInteger nested) {
		this.nested.add(nested);
	}

	public Optional<Integer> getInteger() {
		return value;
	}

	public List<NestedInteger> getList() {
		return nested;
	}
}
