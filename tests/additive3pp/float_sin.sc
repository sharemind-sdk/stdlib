/*
 * This file is a part of the Sharemind framework.
 * Copyright (C) Cybernetica AS
 *
 * All rights are reserved. Reproduction in whole or part is prohibited
 * without the written consent of the copyright owner. The usage of this
 * code is subject to the appropriate license agreement.
 */

module float_sin;

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
void test_sin(T data){
    T max_absolute = 0, max_relative = 0;
    pd_a3p T[[1]] a (20) = {-6,-5,-4,-3,-2,-1,-0.8,-0.6,-0.4,-0.2,0.2,0.4,0.6,0.8,1,2,3,4,5,6};


    T[[1]] b (20) = {
        0.279415498198925872811555446611894759627994864318204318483351,
        0.958924274663138468893154406155993973352461543964601778131672,
        0.756802495307928251372639094511829094135912887336472571485416,
        -0.14112000805986722210074480280811027984693326425226558415188,
        -0.90929742682568169539601986591174484270225497144789026837897,
        -0.84147098480789650665250232163029899962256306079837106567275,
        -0.71735609089952276162717461058138536619278523779142282098968,
        -0.56464247339503535720094544565865790710988808499415177102426,
        -0.38941834230865049166631175679570526459306018344395889511584,
        -0.19866933079506121545941262711838975037020672954020540398639,
        0.198669330795061215459412627118389750370206729540205403986395,
        0.389418342308650491666311756795705264593060183443958895115848,
        0.564642473395035357200945445658657907109888084994151771024265,
        0.717356090899522761627174610581385366192785237791422820989682,
        0.841470984807896506652502321630298999622563060798371065672751,
        0.909297426825681695396019865911744842702254971447890268378973,
        0.141120008059867222100744802808110279846933264252265584151882,
        -0.75680249530792825137263909451182909413591288733647257148541,
        -0.95892427466313846889315440615599397335246154396460177813167,
        -0.27941549819892587281155544661189475962799486431820431848335
    };

    pd_a3p T[[1]] c (20);

    c = sin(a);
    T[[1]] d (20);
    T[[1]] temp(20) = b;

    d = declassify(c) - b;

    for(uint i = 0; i < 20;++i){
        print("Sin(",declassify(a[i]),") = ",declassify(c[i])," Expected: ",b[i]);
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
    print("Sin test: start");
    {
        print("Float32");
        test_sin(0::float32);
    }
    {
        print("Float64");
        test_sin(0::float64);
    }
}
