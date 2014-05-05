/*
 * This file is a part of the Sharemind framework.
 * Copyright (C) Cybernetica AS
 *
 * All rights are reserved. Reproduction in whole or part is prohibited
 * without the written consent of the copyright owner. The usage of this
 * code is subject to the appropriate license agreement.
 */

module float_ln;

import stdlib;
import matrix;
import additive3pp;
import a3p_matrix;
import oblivious;
import a3p_random;
import a3p_sort;
import a3p_bloom;
import x3p_string;
import x3p_aes;
import x3p_join;
import profiling;
import test_utility;


domain pd_a3p additive3pp;


template<type T>
void test_ln(T data){
    T max_absolute = 0, max_relative = 0;
    pd_a3p T[[1]] a (20) = {
        0.1,
        0.2,
        0.3,
        0.4,
        0.5,
        0.6,
        0.7,
        0.8,
        0.9,
        2,
        3,
        4,
        5,
        10,
        50,
        100,
        500,
        1000,
        5000,
        10000
    };


    T[[1]] b (20) = {
        -2.30258509299404568401799145468436420760110148862877297603332,
        -1.60943791243410037460075933322618763952560135426851772191264,
        -1.20397280432593599262274621776183850295361093080602352429863,
        -0.91629073187415506518352721176801107145010121990826246779196,
        -0.69314718055994530941723212145817656807550013436025525412068,
        -0.51082562376599068320551409630366193487811079644576827017795,
        -0.35667494393873237891263871124118447796401675904691178757393,
        -0.22314355131420975576629509030983450337460108554800721367128,
        -0.10536051565782630122750098083931279830612037298327407256393,
        0.693147180559945309417232121458176568075500134360255254120680,
        1.098612288668109691395245236922525704647490557822749451734694,
        1.386294361119890618834464242916353136151000268720510508241360,
        1.609437912434100374600759333226187639525601354268517721912647,
        2.302585092994045684017991454684364207601101488628772976033327,
        3.912023005428146058618750787910551847126702842897290697945975,
        4.605170185988091368035982909368728415202202977257545952066655,
        6.214608098422191742636742242594916054727804331526063673979303,
        6.907755278982137052053974364053092622803304465886318928099983,
        8.517193191416237426654733697279280262328905820154836650012631,
        9.210340371976182736071965818737456830404405954515091904133311
    };

    pd_a3p T[[1]] c (20);

    c = ln(a);
    T[[1]] d (20);
    T[[1]] temp(20) = b;

    d = declassify(c) - b;

    for(uint i = 0; i < 20;++i){
        print("Ln(",declassify(a[i]),") = ",declassify(c[i])," Expected: ",b[i]);
        if(d[i] < 0){d[i] = -d[i];}
        if(temp[i] < 0){temp[i] = -temp[i];}
        print("absolute difference: ",d[i]);
        print("relative difference: ",d[i] / temp[i]);
        print("---------------------------");
    }
    max_absolute = max(d);
    max_relative = max(d / temp);

    print("TEST completed");
    print("Max absolute error: ", max_absolute);
    print("Max relative error: ", max_relative);
    test_report_error(max_relative);
}


void main(){
    print("Ln test: start");
    {
        print("Float32");
        test_ln(0::float32);
    }
    {
        print("Float64");
        test_ln(0::float64);
    }
}
