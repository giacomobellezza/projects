FROM python:3.8-slim-buster

WORKDIR /app

COPY ./flask_demo/requirements.txt /app
RUN pip install -r requirements.txt && \
    python -m spacy download it_core_news_sm
COPY ./flask_demo /app
EXPOSE 5000

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]
