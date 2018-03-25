# gojulutils

Project gojulutils contains various classes I used to reimplement over and over for my job on different projects. To be honest it became quite painful to do it so I decided to share these classes with the community.

This library will be filled over time with new content from the recurring code I have to write.

## Contents

* data : contains various utilities in order to emulate JOIN operations so that stored procedures could be written more easily. This is
especially useful when you have to run algorithms on a datasource made of CSV-like files.

* hibernateutils : contains various hibernate utilities notably a merge tool that can be very useful in order to merge collections.

* validation : a small validation library which contains several handy utilities which simplify writing user input validation stuff.

* filter : a composite implementation for the filter pattern. The goal is to make it simple to split your filter rules and make them easy to test.

* safetools : a collection of tools which solve some safety issues with the standard Java library such as unboxing and so on.

# Remarks

* All classes in this project have their name prefixed with Gojul. The goal is not to put a trademark, but instead to avoid naming collisions with the rest of your project libraries (and even your project classes).

* Project requires Java 8+ to compile. This should not be a problem as Java 7 is no longer supported.
