.global getRendimentoTotal

// float getRendimentoTotal(float *quilometros, float *litros, int size)

// float *quilometros -> r0
// float *litros -> r1
// int size -> r2
// int i -> r3
// float s14 -> rendimentoTotal
// float s15 -> quilometros[i]
// float s16 -> litros[i]

getRendimentoTotal:

stmdb sp!, {r3}                         // Empilha os valores dos registradores que serão usados
vstmdb sp!, {s14, s15, s16}             //

mov r3, #0                              // i = 0
vmov s14, r3                            // rendimentoTotal = 0

getRendimentoTotal_loop:

    cmp r3, r2                          // Se i >= size, termina o loop
    bge fim_getRendimentoTotal_loop     //

    vldmia	r0!, {s15}                  // s15 = quilometros[i]
    vldmia	r1!, {s16}                  // s16 = litros[i]

    vdiv.f32 s15, s15, s16              // rendimentoTotal += quilometros[i]/litros[i]
    vadd.f32 s14, s14, s15              //

    add r3, r3, #1                      // i++
    b getRendimentoTotal_loop           // Reinicia o loop

fim_getRendimentoTotal_loop:

vmov s15, r2                            // s15 = (float) size
vcvt.f32.s32 s15, s15                   //

vdiv.f32 s14, s14, s15                  // s14 = rendimentoTotal/size

vmov r0, s14                            // Define o valor de rendimentoTotal/size para retorno

vldmia sp!, {s14, s15, s16}             // Restaura os registradores empilhados
ldmia sp!, {r3}                         //

bx lr                                   // return total
