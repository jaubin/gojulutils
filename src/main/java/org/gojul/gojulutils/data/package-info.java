/**
 * Package {@code org.gojul.gojulutils.data} contains various data structures and utilities
 * which are designed in order to emulate common database operations. The goal here is to make
 * it easier to rewrite legacy code containing stored procedures with Java. This is especially
 * useful when manipulating data on CSV files, because it avoids using the database in order to
 * run algorithm that should avoid using it for performance reason. However these algorithms are
 * not really suited for very big data sets, because of memory issues.
 */
package org.gojul.gojulutils.data;