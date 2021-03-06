/*
 * Copyright (C) 2015 Cybernetica
 *
 * Research/Commercial License Usage
 * Licensees holding a valid Research License or Commercial License
 * for the Software may use this file according to the written
 * agreement between you and Cybernetica.
 *
 * GNU Lesser General Public License Usage
 * Alternatively, this file may be used under the terms of the GNU Lesser
 * General Public License version 3 as published by the Free Software
 * Foundation and appearing in the file LICENSE.LGPLv3 included in the
 * packaging of this file.  Please review the following information to
 * ensure the GNU Lesser General Public License version 3 requirements
 * will be met: http://www.gnu.org/licenses/lgpl-3.0.html.
 *
 * For further information, please contact us at sharemind@cyber.ee.
 */

/**
@page variables Variables
@brief Variables in SecreC

@section variables_section Variables

Variables in SecreC consist of lowercase and uppercase Latin characters, underscores and decimal digits. The first character of a variable must not be a decimal digit. Reserved keywords are not allowed to be used as variable names.

@subsection declaring_defining Declaring and defining

Variables are declared by writing a type annotation followed by one or more variable names. Optionally, it is possible to assign a value right after the variable declaration by writing an expression after the assignment sign. All declared variables are assigned reasonable default values. For integers this value is 0, for booleans it is **false**.

Listing 1: Some variable declarations
\code
    kind shared3p {
        type bool;
        type uint8;
    }
    domain sharemind_test_pd shared3p;
    domain private shared3p;
    void main () {
        int x; // assigned default value
        int y = 5;
        private uint8 z;
        sharemind_test_pd bool secret = true;
        uint i, j = 2, k;
        return ;
    }
\endcode

As previously mentioned, types are formed by writing security domain before the data type, and **public** may be omitted.
 Array declarations allow the shape (sizes of dimensions) to be specified after the variable name between parenthesis. Initial shape may be non-static and can be an arbitrary public integer expression.
The shape is specified with public signed integer type.

Listing 2: Array declarations
\code
	int [[1]] vector (100); // vector of 100 elements
	bool [[2]] mat (3, 4) = true; // constant true 3x4 matrix
	public int n;
	// some computation on n
	int [[3]] cube (2*n, 3*n, 4*n);
 \endcode

It is possible to define an empty array by not specifying the shape, or by having any of the dimensions have no elements. If an array definition is immediately followed by an assignment, and the shape is not specified then the shape is inherited from the right hand side expression.

Listing 3: (Non)empty arrays
\code
	int [[1]] empty ; // empty array
	int [[1]] over9000 (9001);
	int [[1]] notEmpty = over9000 ;
\endcode

@subsection Scope

The scope of a variable always ends with the containing statement block. Variables with the same name can not be declared within the same scope, but can be overshadowed by declaring a new variable with same name in a deeper nested scope. Global variables never fall out of scope, and can not be overshadowed. Privacy domains, and domain kinds can not be overshadowed. Variables with the same names can be declared in non-overlapping scopes.

Listing 4: Variable overshadowing
\code
	int x = 1;
	{ // nested scope
		int x; // overshadowing !
		int y = 1;
		x = 5;
	}
	// x == 1
	// y is not reachable
\endcode

*/
