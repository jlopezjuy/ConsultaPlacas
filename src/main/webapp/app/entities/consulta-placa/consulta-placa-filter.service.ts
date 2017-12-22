import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporte'
})

@Injectable()
export class SearchfilterReportePipe implements PipeTransform {

    transform(items: any, field: string, value?: any): any {        
        if (value === undefined) return items;
        return items.filter(function (item) {            
            if (value === 'Positivo'){                
                return item[field] === true;
            } else {
                if (value === 'Negativo'){
                    return item[field] === false;
                } else {
                    return item[field] === true || item[field] === false;
                }
            }
        });
    }
}