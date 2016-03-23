.global getMaior

// float getMaior(float *values, int size)

// float *values -> r0
// int size -> r1
// int i -> r2
// float max -> s14
// float value[i] -> s15

getMaior:

stmdb sp!, {r2}                 // Empilha os valores dos registradores que serão usados
vstmdb sp!, {s14, s15}          //

vldmia	r0!, {s14}              // max = values[0]
mov r2, #1                      // i = 1

getMaior_loop:

    cmp r2, r1                  // Se i >= size, termina o loop
    bge fim_getMaior_loop       //

    vldmia	r0!, {s15}          // s15 = values[i]

    vcmpe.f32 s14, s15          //
    vmrs APSR_nzcv, fpscr       // Se max < values[i], max = values[i]
    it	mi                      //
    vmovmi.f32	s14, s15        //

    add r2, r2, #1              // i++
    b getMaior_loop             // Reinicia o loop

fim_getMaior_loop:

vmov r0, s14                    // Define o maior valor pra retorno

vldmia sp!, {s14, s15}          // Restaura os registradores empilhados
ldmia sp!, {r2}                 //

bx lr                           // return max
