package io.flatf.common.file.filter;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;

public class FileNameEndsWithFilter implements FileFilter {

	private final String endsWith;
	private final boolean ignoreCase;

	public FileNameEndsWithFilter(@Nonnull String endsWith) {
		this(endsWith, true);
	}

	public FileNameEndsWithFilter(@Nonnull String endsWith, boolean ignoreCase) throws IllegalArgumentException {
		this.endsWith = endsWith;
		this.ignoreCase = ignoreCase;
	}

	@Override
	public boolean accept(@Nonnull File file) {
		if (ignoreCase) {
			return file.getName().toLowerCase().endsWith(endsWith.toLowerCase());
		} else {
			return file.getName().endsWith(endsWith);
		}
	}

}
