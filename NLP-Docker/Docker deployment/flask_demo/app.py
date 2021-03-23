#!/usr/bin/env python3
# -*- coding: utf-8 -*-

from flask import Flask, request, render_template, url_for
from flasgger import Swagger
import nlp_model
import os

model = nlp_model.nlp_model()

app = Flask(__name__)
swagger = Swagger(app)

# prevent cached responses
@app.after_request
def add_header(r):
    """
    Add headers to both force latest IE rendering engine or Chrome Frame,
    and also to cache the rendered page for 10 minutes.
    """
    r.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    r.headers["Pragma"] = "no-cache"
    r.headers["Expires"] = "0"
    r.headers['Cache-Control'] = 'public, max-age=0'
    return r


@app.route('/')
def hello():
    return 'Hello, World!'


@app.route('/cloudword', methods=["GET", "POST"])
def cloudword_result():
    if request.method == 'POST':
        os.remove("static/cloudword.png")
        text = request.form['text']
        model.cloudword(text)
        return render_template('show.html', url=url_for('static', filename='cloudword.png'))
    else:
        text = request.args.get('text')
        return render_template('form.html', action="http://localhost:5000/cloudword")
        # to-do: improve action


@app.route('/stemming', methods=["GET", "POST"])
def stemming():
    if request.method == 'POST':
        text = request.form['text']
        stemming = model.return_stem(text)
        return ''.join([(str(elem)+' ') for elem in stemming])
    else:
        text = request.args.get('text')
        return render_template('form.html', action="http://localhost:5000/stemming")


@app.route('/cleanword', methods=["GET", "POST"])
def cleanword():
    if request.method == 'POST':
        text = request.form['text']
        cleanword = model.clean_words(text)
        return ''.join([(str(elem)+' ') for elem in cleanword])
    else:
        text = request.args.get('text')
        return render_template('form.html', action="http://localhost:5000/cleanword")


@app.route('/test')
def test():
    return render_template('test.html')


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
