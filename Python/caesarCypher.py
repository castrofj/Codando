#Ceaser Cipher
# https://www.nostarch.com/crackingcodes/ (BSD Licensed)

mode = input('Type e to encrypt or d to decrypt: ')
if mode == 'd' or mode == 'D':
    message = open('encrypted.txt', 'r').read()
elif mode == 'e' or 'E':
    message = input('Enter you message: ')

key = (int)(input('Key to be used: '))

SYMBOLS = 'AÁÂÃÀBCÇDEÉÊFGHIÍJKLMNOÓÔÕPQRSTUÚÜVWXYZaáâãàbcçdeéêfghiíjklmnoóôõpqrstuúüvwxyz1234567890 !?.,'

translated = ''

for symbol in message:
    if symbol in SYMBOLS:
        symbolIndex = SYMBOLS.find(symbol)

        if mode == 'e' or mode == 'E':
            translatedIndex = symbolIndex + key
        elif mode == 'd' or mode == 'D':
            translatedIndex = symbolIndex - key

        if  translatedIndex >= len(SYMBOLS):
            translatedIndex = translatedIndex - len(SYMBOLS)
        elif translatedIndex < 0:
            translatedIndex = translatedIndex + len(SYMBOLS)

        translated = translated + SYMBOLS[translatedIndex]

    else:
        translated = translated + symbol

if mode == 'd' or mode == 'D':
    file = open('decrypted.txt','w')
    file.write(translated)
    file.closed()
elif mode == 'e' or 'E':
    file = open('encrypted.txt','w')
    file.write(translated)
    file.closed()
