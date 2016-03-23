.global getRendimento

// float getRendimento(float quilometros, float litros)

// float quilometros -> r0
// float litros -> r1

getRendimento:

vstmdb sp!, {s14, s15}          // Empilha os valores dos registradores que serão usados

vmov s14, r0
vmov s15, r1

vdiv.f32 s14, s14, s15          // s14 = quilometros/litros

vmov r0, s14                    // Define o valor de quilometros/litros pra retorno

vldmia sp!, {s14, s15}          // Restaura os registradores empilhados

bx lr                           // return quilometros/litros
