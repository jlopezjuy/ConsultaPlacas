import { Injectable, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'searchfilterReporte'
})

@Injectable()
export class SearchfilterReportePipe implements PipeTransform {

    transform(items: any, field: string, value?: any): any {        
        if (value === undefined) return items;
        return items.filter(function (item) {
            return item[field].toLowerCase().includes(value.toLocaleLowerCase());
        });
    }
}