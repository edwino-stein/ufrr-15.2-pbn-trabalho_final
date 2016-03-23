.global getMenor

// float getMenor(float *values, int size)

// float *values -> r0
// int size -> r1
// int i -> r2
// float min -> s14
// float value[i] -> s15

getMenor:

stmdb sp!, {r2}                 // Empilha os valores dos registradores que serão usados
vstmdb sp!, {s14, s15}          //

vldmia	r0!, {s14}              // min = values[0]
mov r2, #1                      // i = 1

getMenor_loop:

    cmp r2, r1                  // Se i >= size, termina o loop
    bge fim_getMenor_loop       //

    vldmia	r0!, {s15}          // s15 = values[i]

    vcmpe.f32 s14, s15          //
    vmrs APSR_nzcv, fpscr       // Se min > values[i], min = values[i]
    it	pl                      //
    vmovpl.f32	s14, s15        //

    add r2, r2, #1              // i++
    b getMenor_loop             // Reinicia o loop

fim_getMenor_loop:

vmov r0, s14                    // Define o menor valor pra retorno

vldmia sp!, {s14, s15}          // Restaura os registradores empilhados
ldmia sp!, {r2}                 //

bx lr                           // return min
