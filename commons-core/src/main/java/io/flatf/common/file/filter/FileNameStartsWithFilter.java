package io.flatf.common.file.filter;

/*
 * #%L
 * ch-commons-util
 * %%
 * Copyright (C) 2012 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;

/**
 * Accepts a file based on whether its filename startsWith a specific string.
 * 
 * @author joelauer (twitter: @jjlauer or
 *         <a href="http://twitter.com/jjlauer" target=
 *         window>http://twitter.com/jjlauer</a>)
 */
public class FileNameStartsWithFilter implements FileFilter {

	private final String startsWith;
	private final boolean ignoreCase;

	public FileNameStartsWithFilter(String startsWith) {
		this(startsWith, false);
	}

	public FileNameStartsWithFilter(String startsWith, boolean ignoreCase) {
		this.startsWith = startsWith;
		this.ignoreCase = ignoreCase;
	}

	@Override
	public boolean accept(@Nonnull File file) {
		if (ignoreCase) {
			return file.getName().toLowerCase().startsWith(startsWith.toLowerCase());
		} else {
			return file.getName().startsWith(startsWith);
		}
	}

}
