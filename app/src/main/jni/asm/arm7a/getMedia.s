.global getMedia

// float getMedia(float *values, int size);

// float *values -> r0
// int size -> r1
// int i -> r2
// float total -> s14
// float value[i] -> s15

getMedia:

stmdb sp!, {r2}                 // Empilha os valores dos registradores que serão usados
vstmdb sp!, {s14, s15}          //

mov r2, #0                      // i = 0
vmov s14, r2                    // total = 0

getMedia_loop:

    cmp r2, r1                  // Se i >= size, termina o loop
    bge fim_getMedia_loop       //

    vldmia	r0!, {s15}          // total += values[i]
    vadd.f32 s14, s14, s15      //

    add r2, r2, #1              // i++
    b getMedia_loop             // Reinicia o loop

fim_getMedia_loop:

vmov s15, r1                    // Converte o size para float
vcvt.f32.s32 s15, s15           //

vdiv.f32 s14, s14, s15          // s14 = total/size

vmov r0, s14                    // Define o valor da media para retorno


vldmia sp!, {s14, s15}          // Restaura os registradores empilhados
ldmia sp!, {r2}                 //

bx lr                           // return total/size
