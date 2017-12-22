import { BaseEntity } from './../../shared';

export class ConsultaPlaca implements BaseEntity {
    constructor(
        public id?: number,
        public fecha?: any,
        public consulta?: string,
        public metodo?: string,
        public estado?: boolean,
        public resultado?: string,
        public coordenadas?: string,
        public radioIssi?: number,
        public radioResponsable?: string,
        public radioMmunicipio?: string,
        public radioCorporacion?: string
    ) {
        this.estado = false;
    }
}
