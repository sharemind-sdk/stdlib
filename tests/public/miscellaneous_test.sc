/*
 * This file is a part of the Sharemind framework.
 * Copyright (C) Cybernetica AS
 *
 * All rights are reserved. Reproduction in whole or part is prohibited
 * without the written consent of the copyright owner. The usage of this
 * code is subject to the appropriate license agreement.
 */

module miscellaneous_test;

import stdlib;
import matrix;
import shared3p;
import shared3p_matrix;
import oblivious;
import shared3p_random;
import shared3p_sort;
import shared3p_bloom;
import shared3p_string;
import shared3p_aes;
import shared3p_join;
import profiling;
import test_utility;

domain pd_shared3p shared3p;

public uint all_tests;
public uint succeeded_tests;

template<type T>
T random_float(T data){
    T rand = 1;
    for(uint i = 0; i < 2; ++i){
        pd_shared3p uint32 temp;
        pd_shared3p int8 temp2;
        while(declassify(temp) == 0 || declassify(temp2) == 0){
            temp = randomize(temp);
            temp2 = randomize(temp2);
        }
        T scalar = (T) declassify(temp);
        T scalar2 = (T) declassify(temp2);
        if((i % 2) == 0){
            rand *= scalar;
            rand *= scalar2;
        }
        else{
            rand /= scalar;
            rand /= scalar2;
        }
    }
    return rand;
}

template<domain D: shared3p,type T>
D T[[1]] random(D T[[1]] data){
    uint x_shape = shape(data)[0];
    T[[1]] temp (x_shape);
    for(uint i = 0; i < x_shape;++i){
        temp[i] = random_float(0::T);
    }
    D T[[1]] result = temp;
    return result;
}

template<type T>
void neg_values(T data){
    pd_shared3p T a;
    pd_shared3p T[[1]] b (5);
    a = randomize(a); b = randomize(b);
    T c = declassify(-a);
    if((declassify(a) + c) == 0){
        print("SUCCESS!");
        all_tests += 1;
        succeeded_tests += 1;
    }
    else{
        print("FAILURE! negation failed");
        all_tests += 1;
    }
    T[[1]] d (5) = declassify(-b);
    c = declassify(-a);
    if(all((declassify(b) + d) == 0)){
        print("SUCCESS!");
        all_tests += 1;
        succeeded_tests += 1;
    }
    else{
        print("FAILURE! negation failed");
        all_tests += 1;
    }
}

template<type T>
void neg_values_float(T data){
    pd_shared3p T a;
    pd_shared3p T[[1]] b (5);
    a = classify(random_float(0::T)); b = random(b);
    T c = declassify(-a);
    if((declassify(a) + c) == 0){
        print("SUCCESS!");
        all_tests += 1;
        succeeded_tests += 1;
    }
    else{
        print("FAILURE! negation failed");
        all_tests += 1;
    }
    T[[1]] d (5) = declassify(-b);
    c = declassify(-a);
    if(all((declassify(b) + d) == 0)){
        print("SUCCESS!");
        all_tests += 1;
        succeeded_tests += 1;
    }
    else{
        print("FAILURE! negation failed");
        all_tests += 1;
    }
}

template<type T>
void cast_bool_uint(T data){
    //public
    bool result = true;
    pd_shared3p bool[[1]] temp (5);
    bool[[1]] a = declassify(randomize(temp));
    T[[1]] b (5) = (T)a;
    for(uint i = 0; i < 5; ++i){
        if(a[i] == true && b[i] == 0){
            result = false;
        }
        if(a[i] == false && b[i] == 1){
            result = false;
        }
    }
    if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
    }
    else{
        print("SUCCESS!");
        succeeded_tests +=1;
        all_tests += 1;
    }
}

template<type T>
void cast_uint_bool(T data){
    // public
    bool result = true;
    pd_shared3p T[[1]] temp (5);
    T[[1]] a = declassify(randomize(temp));
    T[[1]] c (5);
    for(uint i = 0; i < size(a);++i){
        if(a[i] % 2 == 0){
            c[i] = 0;
        }
        else{
            c[i] = 1;
        }
    }
    bool[[1]] b (5) = (bool)c;

    for(uint i = 0; i < 5; ++i){
        if(b[i] == true && c[i] == 0){
            result = false;
        }
        if(b[i] == false && c[i] == 1){
            result = false;
        }
    }
    if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
    }
    else{
        print("SUCCESS!");
        succeeded_tests +=1;
        all_tests += 1;
    }
}


void main(){

    print("Miscellaneous test: start");

    print("TEST 1: negating values");
    {
        print("uint8");
        neg_values(0::uint8);
    }
    {
        print("uint16");
        neg_values(0::uint16);
    }
    {
        print("uint32");
        neg_values(0::uint32);
    }
    {
        print("uint64");
        neg_values(0::uint);
    }
    {
        print("int8");
        neg_values(0::int8);
    }
    {
        print("int16");
        neg_values(0::int16);
    }
    {
        print("int32");
        neg_values(0::int32);
    }
    {
        print("int64");
        neg_values(0::int);
    }
    {
        print("float32");
        neg_values_float(0::float32);
    }
    {
        print("float64");
        neg_values_float(0::float64);
    }








    print("TEST 2: casting values");
    {
        print("bool -> uint8");
        cast_bool_uint(0::uint8);
    }
    {
        print("bool -> uint16");
        cast_bool_uint(0::uint16);
    }
    {
        print("bool -> uint32");
        cast_bool_uint(0::uint32);
    }
    {
        print("bool -> uint");
        cast_bool_uint(0::uint);
    }
    {
        print("bool -> int8");
        cast_bool_uint(0::int8);
    }
    {
        print("bool -> int16");
        cast_bool_uint(0::int16);
    }
    {
        print("bool -> int32");
        cast_bool_uint(0::int32);
    }
    {
        print("bool -> int");
        cast_bool_uint(0::int);
    }
    {
        print("bool -> float32");
        cast_bool_uint(0::float32);
    }
    {
        print("bool -> float64");
        cast_bool_uint(0::float64);
    }
    print("TEST 3: casting values (2)");
    {
        print("uint8 -> bool");
        cast_uint_bool(0::uint8);
    }
    {
        print("uint16 -> bool");
        cast_uint_bool(0::uint16);
    }
    {
        print("uint32 -> bool");
        cast_uint_bool(0::uint32);
    }
    {
        print("uint64 -> bool");
        cast_uint_bool(0::uint);
    }
    {
        print("int8 -> bool");
        cast_uint_bool(0::int8);
    }
    {
        print("int16 -> bool");
        cast_uint_bool(0::int16);
    }
    {
        print("int32 -> bool");
        cast_uint_bool(0::int32);
    }
    {
        print("int64 -> bool");
        cast_uint_bool(0::int);
    }









    print("TEST 4: casting values(3)");
    {
        print("uint8 -> int8");
        bool result = true;
        uint8[[1]] a = {0,100,200,255};
        {
            //int8
            int8[[1]] b = (int8)(a::uint8);
            int8[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
            uint8[[1]] temp = a;
            int8[[1]] c = (int8)(temp::uint8);
            if(any(c != control)){result = false;}
        }
        {
            //int16
            int16[[1]] b = (int16)(a::uint8);
            int16[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::uint8);
            int32[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)(a::uint8);
            int[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint16 -> int");
        bool result = true;
        uint16[[1]] a = {0,15385,38574,65535};
        {
            //int8
            int8[[1]] b = (int8)(a::uint16);
            int8[[1]] control = {0,25,-82,-1};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            int16[[1]] b = (int16)(a::uint16);
            int16[[1]] control = {0,15385,-26962,-1};
            if(any(b != control)){result = false;}
            uint16[[1]] temp = a;
            int16[[1]] c = (int16)(temp::uint16);
            if(any(c != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::uint16);
            int32[[1]] control = {0,15385,38574,65535};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)(a::uint16);
            int[[1]] control = {0,15385,38574,65535};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint32 -> int");
        bool result = true;
        uint32[[1]] a = {0,21424,21525341,4294967295};
        {
            //int8
            int8[[1]] b = (int8)(a::uint32);
            int8[[1]] control = {0,-80,93,-1};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            int16[[1]] b = (int16)(a::uint32);
            int16[[1]] control = {0,21424,29533,-1};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::uint32);
            int32[[1]] control = {0,21424,21525341,-1};
            if(any(b != control)){result = false;}
            uint32[[1]] temp = a;
            int32[[1]] c = (int32)(temp::uint32);
            if(any(c != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)(a::uint32);
            int[[1]] control = {0,21424,21525341,4294967295};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint64 -> int");
        bool result = true;
        uint[[1]] a = {0,55161532,142234215413552,18446744073709551615};
        {
            //int8
            int8[[1]] b = (int8)(a::uint);
            int8[[1]] control = {0,-68,48,-1};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            int16[[1]] b = (int16)(a::uint);
            int16[[1]] control = {0,-19780,30512,-1};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::uint);
            int32[[1]] control = {0, 55161532, 2078439216, -1};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)a;
            int[[1]] control = {0,55161532,142234215413552,-1};
            if(any(b != control)){result = false;}
            uint[[1]] temp = (a::uint);
            int[[1]] c = (int)(temp::uint);
            if(any(c != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }










    print("TEST 5: casting values(4)");
    {
        print("int8 -> uint");
        bool result = true;
        int8[[1]] a = {-128,-40,40,127};
        {
            //int8
            uint8[[1]] b = (uint8) (a::int8);
            uint8[[1]] control = {128,216,40,127};
            if(any(b != control)){result = false;}
            int8[[1]] temp = a;
            uint8[[1]] c = (uint8)(temp::int8);
            if(any(c != control)){result = false;}
        }
        {
            //int16
            uint16[[1]] b = (uint16)(a::int8);
            uint16[[1]] control = {65408,65496,40,127};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::int8);
            uint32[[1]] control = {4294967168,4294967256,40,127};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)(a::int8);
            uint[[1]] control = {18446744073709551488,18446744073709551576,40,127};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int16 -> uint");
        bool result = true;
        int16[[1]] a = {-32768,-16325,12435,32767};
        {
            //int8
            uint8[[1]] b = (uint8)(a::int16);
            uint8[[1]] control = {0,59,147,255};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            uint16[[1]] b = (uint16)(a::int16);
            uint16[[1]] control = {32768,49211,12435,32767};
            if(any(b != control)){result = false;}
            int16[[1]] temp = a;
            uint16[[1]] c = (uint16)(temp::int16);
            if(any(c != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::int16);
            uint32[[1]] control = {4294934528, 4294950971,12435,32767};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)(a::int16);
            uint[[1]] control = {18446744073709518848,18446744073709535291,12435,32767};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int32 -> uint");
        bool result = true;
        int32[[1]] a = {-2147483648,-483648,2147483,2147483647};
        {
            //int8
            uint8[[1]] b = (uint8)(a::int32);
            uint8[[1]] control = {0,192,155,255};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            uint16[[1]] b = (uint16)(a::int32);
            uint16[[1]] control = {0,40640,50331,65535};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::int32);
            uint32[[1]] control = {2147483648, 4294483648, 2147483, 2147483647};
            if(any(b != control)){result = false;}
            int32[[1]] temp = (a::int32);
            uint32[[1]] c = (uint32)(temp::int32);
            if(any(c != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)(a::int32);
            uint[[1]] control = {18446744071562067968, 18446744073709067968, 2147483, 2147483647};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int64 -> uint");
        bool result = true;
        int[[1]] a = {-9223372036854775808,-7036854775808,9223372036854,9223372036854775807};
        {
            //int8
            uint8[[1]] b = (uint8)(a::int);
            uint8[[1]] control =  {0, 0, 246, 255};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            uint16[[1]] b = (uint16)(a::int);
            uint16[[1]] control = {0, 20480, 23286, 65535};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::int);
            uint32[[1]] control = {0, 2596622336, 2077252342, 4294967295};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)a;
            uint[[1]] control = {9223372036854775808, 18446737036854775808, 9223372036854, 9223372036854775807};
            if(any(b != control)){result = false;}
            int[[1]] temp = (a::int);
            uint[[1]] c = (uint)(temp::int);
            if(any(c != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }





    print("TEST 6: Casting values(5)");
    {
        print("uint8 -> uint");
        bool result = true;
        uint8[[1]] a = {0,100,200,255};
        {
            //uint16
            uint16[[1]] b = (uint16)(a::uint8);
            uint16[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::uint8);
            uint32[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)(a::uint8);
            uint[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint16 -> uint");
        bool result = true;
        uint16[[1]] a = {0,15385,38574,65535};
        {
            //int8
            uint8[[1]] b = (uint8)(a::uint16);
            uint8[[1]] control = {0, 25, 174, 255};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::uint16);
            uint32[[1]] control = {0,15385,38574,65535};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)(a::uint16);
            uint[[1]] control = {0,15385,38574,65535};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint32 -> uint");
        bool result = true;
        uint32[[1]] a = {0,21424,21525341,4294967295};
        {
            //int8
            uint8[[1]] b = (uint8)(a::uint32);
            uint8[[1]] control = {0, 176, 93, 255};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            uint16[[1]] b = (uint16)(a::uint32);
            uint16[[1]] control = {0, 21424, 29533, 65535};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            uint[[1]] b = (uint)(a::uint32);
            uint[[1]] control = {0,21424,21525341,4294967295};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint64 -> uint");
        bool result = true;
        uint[[1]] a = {0,55161532,142234215413552,18446744073709551615};
        {
            //int8
            uint8[[1]] b = (uint8)(a::uint);
            uint8[[1]] control = {0, 188, 48, 255};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            uint16[[1]] b = (uint16)(a::uint);
            uint16[[1]] control = {0, 45756, 30512, 65535};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            uint32[[1]] b = (uint32)(a::uint);
            uint32[[1]] control = {0, 55161532, 2078439216, 4294967295};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }






    {
        print("int8 -> int");
        bool result = true;
        int8[[1]] a = {-128,-40,40,127};
        {
            //int16
            int16[[1]] b = (int16)(a::int8);
            int16[[1]] control = {-128,-40,40,127};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::int8);
            int32[[1]] control = {-128,-40,40,127};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)(a::int8);
            int[[1]] control = {-128,-40,40,127};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int16 -> int");
        bool result = true;
        int16[[1]] a = {-32768,-16325,12435,32767};
        {
            //int8
            int8[[1]] b = (int8)(a::int16);
            int8[[1]] control = {0, 59, -109, -1};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::int16);
            int32[[1]] control = {-32768,-16325,12435,32767};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)(a::int16);
            int[[1]] control = {-32768,-16325,12435,32767};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int32 -> int");
        bool result = true;
        int32[[1]] a = {-2147483648,-483648,2147483,2147483647};
        {
            //int8
            int8[[1]] b = (int8)(a::int32);
            int8[[1]] control = {0, -64, -101, -1};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            int16[[1]] b = (int16)(a::int32);
            int16[[1]] control =  {0, -24896, -15205, -1};
            if(any(b != control)){result = false;}
        }
        {
            //int64
            int[[1]] b = (int)(a::int32);
            int[[1]] control = {-2147483648,-483648,2147483,2147483647};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int64 -> int");
        bool result = true;
        int[[1]] a = {-9223372036854775808,-7036854775808,9223372036854,9223372036854775807};
        {
            //int8
            int8[[1]] b = (int8)(a::int);
            int8[[1]] control =  {0, 0, -10, -1};
            if(any(b != control)){result = false;}
        }
        {
            //int16
            int16[[1]] b = (int16)(a::int);
            int16[[1]] control = {0, 20480, 23286, -1};
            if(any(b != control)){result = false;}
        }
        {
            //int32
            int32[[1]] b = (int32)(a::int);
            int32[[1]] control = {0, -1698344960, 2077252342, -1};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }




    print("TEST 7: Casting values(6)");
    {
        print("uint8 -> float32/64");
        bool result = true;
        uint8[[1]] a = {0,100,200,255};
        {
            float32[[1]] b = (float32)(a::uint8);
            float32[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::uint8);
            float64[[1]] control = {0,100,200,255};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint16 -> float32/64");
        bool result = true;
        uint16[[1]] a = {0,15385,38574,65535};
        {
            float32[[1]] b = (float32)(a::uint16);
            float32[[1]] control = {0,15385,38574,65535};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::uint16);
            float64[[1]] control = {0,15385,38574,65535};
            if(any(b != control)){result = false;}
        }
        if(!result){
            print("FAILURE! casting failed");
            all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint32 -> float32/64");
        bool result = true;
        uint32[[1]] a = {0,21424,21525341,4294967295};
        {
            float32[[1]] b = (float32)(a::uint32);
            float32[[1]] control = {0,21424,21525341,4294967295};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::uint32);
            float64[[1]] control = {0,21424,21525341,4294967295};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("uint64 -> float64");
        bool result = true;
        uint[[1]] a = {0,55161532,142234215413552,18446744073709551615};
        {
            float64[[1]] b = (float64)(a::uint);
            float64[[1]] control = {0,55161532,142234215413552,18446744073709551615};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int8 -> Float32/64");
        bool result = true;
        int8[[1]] a = {-128,-40,40,127};
        {
            float32[[1]] b = (float32)(a::int8);
            float32[[1]] control = {-128,-40,40,127};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::int8);
            float64[[1]] control = {-128,-40,40,127};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int16 -> Float32/64");
        bool result = true;
        int16[[1]] a = {-32768,-16325,12435,32767};
        {
            float32[[1]] b = (float32)(a::int16);
            float32[[1]] control = {-32768,-16325,12435,32767};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::int16);
            float64[[1]] control = {-32768,-16325,12435,32767};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int32 -> Float32/64");
        bool result = true;
        int32[[1]] a = {-2147483648,-483648,2147483,2147483647};
        {
            float32[[1]] b = (float32)(a::int32);
            float32[[1]] control = {-2147483648,-483648,2147483,2147483647};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::int32);
            float64[[1]] control =  {-2147483648,-483648,2147483,2147483647};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }
    {
        print("int64 -> Float32/64");
        bool result = true;
        int[[1]] a = {-9223372036854775808,-7036854775808,9223372036854,9223372036854775807};
        {
            float32[[1]] b = (float32)(a::int);
            float32[[1]] control = {-9223372036854775808,-7036854775808,9223372036854,9223372036854775807};
            if(any(b != control)){result = false;}
        }
        {
            float64[[1]] b = (float64)(a::int);
            float64[[1]] control = {-9223372036854775808,-7036854775808,9223372036854,9223372036854775807};
            if(any(b != control)){result = false;}
        }
        if(!result){
        print("FAILURE! casting failed");
        all_tests += 1;
        }
        else{
            print("SUCCESS!");
            succeeded_tests +=1;
            all_tests += 1;
        }
    }

    print("Test finished!");
    print("Succeeded tests: ", succeeded_tests);
    print("Failed tests: ", all_tests - succeeded_tests);

    test_report(all_tests, succeeded_tests);
}
