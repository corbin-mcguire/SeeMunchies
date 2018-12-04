import json
import os
import re
import cStringIO
from flask import Flask, Response, request
import pprint

# New Imports for Mongo:
from pymongo import MongoClient # New
from flask_pymongo import PyMongo # New

# Imports for find_food.py
from PIL import Image
from io import BytesIO
import base64
import find_food

app = Flask(__name__)
app.run(port=5000)

# New
try:
    app.config['MONGO_DBNAME'] = 'seefood_pictures'
    app.config['MONGO_URI'] = 'mongodb://localhost:27017/seefood_pictures'
    client = MongoClient()
    db = client['database']
    app.debug = True
    db = client.app
    mongo = PyMongo(app)
    db = client.database
    food_hoard = db.food
    print('Connection Successful! Database Exists!')
except:
    print('Could not connect to MongoDB')

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

#new
if __name__ == '__main__':
    app.run(debug=True)


# test for server response
@app.route('/') 
def home():
    data = {
        'hello'  : 'world',
        'number' : 3
    }
    js = json.dumps(data)    
    resp = Response(js, status=200, mimetype='application/json')
    resp.headers['Link'] = 'http://luisrei.com'
    return resp

# Args stuff // POST Request // How to Accept a post request
@app.route('/json_post', methods=['POST'])
def json_post() :
    # The following variables are what can be found in the query string. It is here where the values will be decoded.
    req_data = request.get_json()
    data = req_data['value']
    js = json.dumps(data)
    print(js)
    js = js.replace(unichr(34), '')
    
    # Old
    image = Image.open(BytesIO(base64.b64decode(js)))
    result = find_food.food(image)
    result1 = ''.join(str(e)for e in result)
    resp = Response(result1, status=200, mimetype='application/json')
    print(js)
    
    image_value = {
        'image': js,
        'stat' : result1
    }

    print('json value made')

    try:
        # Insert into the mongodb
        food_hoard.insert_one(image_value)
        print('Inserted Successfully')
    except:
        print('Did not work - Not in MongoDB :(')

    return resp

@app.route('/get_data', methods=['GET', 'POST'])
def get_data():
    # Get data out of mongodb Query the database for value.
    try:
        cursor = food_hoard.find({})
        print('Got Contents')
        x = []

        for document in cursor:
            x.append(document)
        #    pprint.pprint(document)
        print(x)
        value = ''.join(str(x)) 
        return value

    except:
        print('Did not Worked')
