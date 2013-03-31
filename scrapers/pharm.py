# coding=utf-8
""" Parse the pharmacists from a webpage into a JSON struct and write it to a file. """
from __future__ import with_statement
import json, urllib, libxml2, os, os.path, datetime, locale, re
from datetime import datetime, timedelta

SOURCE = 'http://admin.ringring.be/apb/public/duty_geo2.asp?lan=1&city=Gent&street_address=&zip_code=9000&T_dag=%02d&T_maand=%02d&T_jaar=%04d&T_hour=%04s&textv=1&printable=1'
API_PATH = './'

def download_pharms(day):
	page = get_pharm_page(SOURCE, day)
	pharms = parse_pharms_from_html(page)
	if pharms:
		dump_pharms_to_file(API_PATH, pharms)

def get_pharm_page(url, day):
	hour = '%02d%02d' % (day.hour, day.minute)
	f = urllib.urlopen(url % (day.day, day.month, day.year, hour))
	return f.read()

def parse_pharms_from_html(page):
	print('Parsing webpage to an object tree')
	# replace those pesky non-breakable spaces
	page = page.replace('&nbsp;', ' ')

	doc = libxml2.htmlReadDoc(page, None, 'utf-8', libxml2.XML_PARSE_RECOVER | libxml2.XML_PARSE_NOERROR)
	menuElement = doc.xpathEval("//*[starts-with(@id, 'listResults')]")
	#1 pharmacist on each row
	rows = menuElement[0].xpathEval('.//tr')[0:]
	pharmacists = {}
	pharmacists['Apotheken']=[]
	for row in rows:
		for name in row.xpathEval('.//b')[0:]:
			#Valid name
			if (str.isupper(name.content[1])):
				name=name.content.strip().title()
				open=row.xpathEval('.//span')[0:][0].content.strip()
				m=re.search(r" {4}([^0-9]*) ([0-9]*) {4}([0-9]{4}) ([A-Z,\-]*)",row.content)
				n=re.search(r"([0-9]{5,})",row.content)
				pharmacist={'name':name,
							'open':open,
							'street': m.group(1).strip(),
							'nr': m.group(2).strip(),
							'zip': m.group(3).strip(),
							'city': m.group(4).strip().title(),
							'tel': n.group(1)
				}
				pharmacists['Apotheken'].extend([pharmacist])
	return pharmacists

def dump_pharms_to_file(path, pharms):
	print('Writing object tree to file in JSON format')
	if not os.path.isdir(path):
		os.makedirs(path)
	with open('%s/em_pharms.json' % (path), 'w') as f:
		json.dump(pharms, f, sort_keys=True)

if __name__ == "__main__":
	days = [datetime.today() + timedelta(days = n) for n in range(1)]
	for day in days:
		download_pharms(day)
