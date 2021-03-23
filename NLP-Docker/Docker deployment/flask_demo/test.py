import nlp_model 

model = nlp_model.nlp_model()
plt = model.cloudword("Ti scrivo un test per vcapire se funzione, facciamo questa prova. E un test sincero")
plt.savefig('cloudword.png')
plt.show()